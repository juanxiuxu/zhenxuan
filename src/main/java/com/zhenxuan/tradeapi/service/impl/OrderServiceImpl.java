package com.zhenxuan.tradeapi.service.impl;

import com.zhenxuan.tradeapi.common.enums.Member;
import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.entity.GoodsSkuItem;
import com.zhenxuan.tradeapi.entity.GoodsSpuItem;
import com.zhenxuan.tradeapi.entity.UserAuthEntity;
import com.zhenxuan.tradeapi.mapper.OrderItemMapper;
import com.zhenxuan.tradeapi.mapper.OrderMapper;
import com.zhenxuan.tradeapi.mapper.UserAuthMapper;
import com.zhenxuan.tradeapi.service.OrderService;
import com.zhenxuan.tradeapi.service.UserService;
import com.zhenxuan.tradeapi.thirdparty.DynamoDbService;
import com.zhenxuan.tradeapi.vo.CreateOrderDirectReqVo;
import com.zhenxuan.tradeapi.vo.CreateOrderDirectRespVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private DynamoDbService dynamoDbService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public CreateOrderDirectRespVo createOrderDirect(CreateOrderDirectReqVo createOrderDirectReqVo) {
        UserAuthEntity authEntity = userService.verifyAuthUid(createOrderDirectReqVo.getAuthUid());

        String skuId = createOrderDirectReqVo.getSkuId();
        GoodsSpuItem.SkuInfo skuInfo = getOrderItemBySkuId(skuId);
        if (skuInfo == null) {
            logger.error("SkuInfo for skuId:{} not found", skuId);
            throw new ZXException(ResultStatusCode.NO_GOODS_FOR_SKUID);
        }

        if (!skuInfo.isShow()) {
            throw new ZXException(ResultStatusCode.GOODS_NOT_SHOW);
        }
        if (skuInfo.getStockCount() < createOrderDirectReqVo.getPurchseCount()) {
            throw new ZXException(ResultStatusCode.GOODS_STOCK_NOT_ENOUGH);
        }


        return null;
    }

    // 从dynamodb获取sku信息
    private GoodsSpuItem.SkuInfo getOrderItemBySkuId(String skuId) {
        String spuId = parseSpuIdFromSkuId(skuId);

        GoodsSpuItem spuItem = dynamoDbService.read(GoodsSpuItem.class, spuId, "0", true);
        if (spuItem == null) {
            logger.error("spu item not found. spudId:{}", spuId);
            throw new ZXException(ResultStatusCode.SPUITEM_NOT_FOUND);
        }
        List<GoodsSpuItem.SkuInfo> skuInfos = spuItem.getSkuInfos();
        if (CollectionUtils.isEmpty(skuInfos)) {
            logger.error("goods data of dynamodb error, no skus in spu");
            throw new ZXException(ResultStatusCode.GOODS_DATA_ERROR);
        }

        List<GoodsSkuItem> skuItems = dynamoDbService.query(GoodsSkuItem.class, "gid", spuId, "sid", spuId + "-", DynamoDbService.QueryOp.BeginWith, true);
        if (CollectionUtils.isEmpty(skuItems)) {
            logger.error("sku items not found. spuId:{}", spuId);
            throw new ZXException(ResultStatusCode.SKUITEMS_NOT_FOUND);
        }
        if (skuInfos.size() != skuItems.size()) {
            logger.error("sku data not matched in dynamodb. size in spu={}, size of sku records={}", skuInfos.size(), skuItems.size());
            throw new ZXException(ResultStatusCode.GOODS_DATA_ERROR);
        }

        for (GoodsSpuItem.SkuInfo skuInfo : skuInfos) {
            if (skuId.equals(skuInfo.getSkuId())) {
                for (GoodsSkuItem skuItem : skuItems) {
                    if (skuId.equals(skuItem.getSkuId())) {
                        skuInfo.setStockCount(skuItem.getStockCount());
                        skuInfo.setSalesCount(skuItem.getBaseSalesCount() + skuItem.getSalesCount());
                        return skuInfo;
                    }
                }
            }
        }

        return null;
    }

    private String parseSpuIdFromSkuId(String skuId) {
        if (skuId == null) {
            return null;
        }

        String[] segments = skuId.split("-");
        if (segments == null || segments.length != 2 || segments[0] == "" || segments[1] == "") {
            logger.error("invalid skuId:{}", skuId);
            throw new ZXException(ResultStatusCode.INVALID_SKUID_FORMAT);
        }

        return segments[0];
    }

//    private int calcFreight(String province, String city) {
//
//    }

    private String judgeCashbackedTid(UserAuthEntity authEntity, CreateOrderDirectReqVo reqVo) {
        Member member = Member.fromCode(authEntity.getMember());
        String cachebackedTid = "0";
        if (authEntity.getMember() > Member.VIP.code) {
            // 如果用户是店主以上，那么订单的tid设定为自己
            cachebackedTid = reqVo.getAuthUid();
            return cachebackedTid;
        }

        if (!StringUtils.isEmpty(authEntity.getTid())) {
            cachebackedTid = authEntity.getTid();
            return cachebackedTid;
        }

        UserAuthEntity iuidEntity = userAuthMapper.selectEntityByUid(authEntity.getIuid());
        if (iuidEntity == null) {
            return cachebackedTid;
        }
        if (!iuidEntity.isMemberExpired()) {
            cachebackedTid = authEntity.getIuid();
        }

        return cachebackedTid;
    }
}
