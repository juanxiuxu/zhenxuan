package com.zhenxuan.tradeapi.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.Member;
import com.zhenxuan.tradeapi.common.enums.OrderStatus;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.domain.GoodsSkuInfo;
import com.zhenxuan.tradeapi.domain.GoodsSpuInfo;
import com.zhenxuan.tradeapi.entity.*;
import com.zhenxuan.tradeapi.mapper.OrderItemMapper;
import com.zhenxuan.tradeapi.mapper.OrderMapper;
import com.zhenxuan.tradeapi.mapper.ProductMapper;
import com.zhenxuan.tradeapi.mapper.UserAuthMapper;
import com.zhenxuan.tradeapi.service.OrderService;
import com.zhenxuan.tradeapi.service.UserService;
import com.zhenxuan.tradeapi.thirdparty.DynamoDbService;
import com.zhenxuan.tradeapi.utils.GlobalIdUtil;
import com.zhenxuan.tradeapi.vo.CreateOrderDirectReqVo;
import com.zhenxuan.tradeapi.vo.CreateOrderDirectRespVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CreateOrderDirectRespVo createOrderDirect(CreateOrderDirectReqVo createOrderDirectReqVo) {
        UserAuthEntity authEntity = userService.verifyAuthUid(createOrderDirectReqVo.getAuthUid());
        int purchaseCount = createOrderDirectReqVo.getPurchaseCount();
        String skuId = createOrderDirectReqVo.getSkuId();
        String spuId = parseSpuIdFromSkuId(skuId);

        GoodsSpuInfo spuInfo = getSpuInfo(spuId);
        if (spuInfo == null) {
            logger.error("SpuInfo for spuId:{} not found", spuId);
            throw new ZXException(ResultStatusCode.NO_GOODS_FOR_SPUID);
        }

        GoodsSkuInfo skuInfo = getSkuInfo(spuInfo, skuId);
        if (skuInfo == null) {
            logger.error("SkuInfo for skuId:{} not found", skuId);
            throw new ZXException(ResultStatusCode.NO_GOODS_FOR_SKUID);
        }

        if (!skuInfo.isShow()) {
            throw new ZXException(ResultStatusCode.GOODS_NOT_SHOW);
        }
        if (skuInfo.getStockCount() < purchaseCount) {
            throw new ZXException(ResultStatusCode.GOODS_STOCK_NOT_ENOUGH);
        }

        OrderEntity orderEntity = createOrder(authEntity, spuInfo, skuInfo, createOrderDirectReqVo);
        OrderItemEntity orderItemEntity = createOrderItem(orderEntity, authEntity, spuInfo, skuInfo, createOrderDirectReqVo);
        try {
            SaveOrder(orderItemEntity, orderEntity);
        } catch (Exception e) {
            logger.error("save order is failed. err:{}", e);
            throw new ZXException(ResultStatusCode.CREATE_ORDER_FAILED);
        }

        CreateOrderDirectRespVo orderDirectRespVo = new CreateOrderDirectRespVo();
        orderDirectRespVo.setOrderId(orderEntity.getOid());

        return orderDirectRespVo;
    }

    public OrderEntity createOrder(UserAuthEntity authEntity, GoodsSpuInfo spuInfo, GoodsSkuInfo skuInfo, CreateOrderDirectReqVo reqVo) {
        int freight = calculateTotalFreight(spuInfo, reqVo);
        int goodsPrice = calculateTotalGoodsPrice(skuInfo, reqVo.getPurchaseCount());
        int cashBack = calculateCachBack(skuInfo, authEntity, reqVo.getPurchaseCount());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAuthUid(reqVo.getAuthUid());
        orderEntity.setCouponCode(reqVo.getCouponCode());
        orderEntity.setIuid(authEntity.getIuid());
        orderEntity.setTid(judgeCashBackedTid(authEntity, reqVo));
        orderEntity.setGoodsTotal(goodsPrice);
        orderEntity.setVip(authEntity.getMember());
        orderEntity.setTotal(goodsPrice + freight);
        orderEntity.setCashback(cashBack);
        orderEntity.setOp(spuInfo.getOp());
        orderEntity.setOrderStatus(OrderStatus.PAY_WAITING.code);
        orderEntity.setOid(GlobalIdUtil.newOrderId());

        return orderEntity;
    }

    public OrderItemEntity createOrderItem(OrderEntity orderEntity, UserAuthEntity authEntity, GoodsSpuInfo spuInfo, GoodsSkuInfo skuInfo, CreateOrderDirectReqVo reqVo) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setorderItemId(orderEntity.getOid());
        orderItemEntity.setOid(orderEntity.getOid());
        orderItemEntity.setAuthUid(authEntity.getAuthUid());
        orderItemEntity.setOp(spuInfo.getOp());
        orderItemEntity.setSid(skuInfo.getSkuId());
        //  orderItemEntity.setBarcode
        orderItemEntity.setSpuId(spuInfo.getSpuId());
        orderItemEntity.setSpuName(spuInfo.getSpuName());
        //orderItemEntity.setSkuName(skuInfo.);
        //orderItemEntity.setCover();
        orderItemEntity.setNum(reqVo.getPurchaseCount());
        orderItemEntity.setUnitPrice((int)skuInfo.getPrice());
        //orderItemEntity.setCode
        //orderItemEntity.setrNum(skuInfo.);
        orderItemEntity.setTid(orderEntity.getTid());
        orderItemEntity.setIuid(orderEntity.getIuid());
        String avaStatus = authEntity.getAvatar();
        orderItemEntity.setEvaStatus(Integer.parseInt(avaStatus));
        //orderItemEntity.setCommentId(spuItem.);

        return orderItemEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void SaveOrder(OrderItemEntity orderItemEntity, OrderEntity orderEntity) {
        orderItemMapper.insertOrderItem(orderItemEntity);
        orderMapper.insertOrder(orderEntity);
        String skuId = orderItemEntity.getSid();
        int count = orderItemEntity.getNum();
        productMapper.decreaseStockCount(skuId, count);
    }

    // 从dynamo db读spu信息
    private GoodsSpuInfo getSpuInfo(String spuId) {
        GoodsSpuItem spuItem = dynamoDbService.read(GoodsSpuItem.class, spuId, "0", true);
        if (spuItem == null) {
            logger.error("spu item not found. spudId:{}", spuId);
            throw new ZXException(ResultStatusCode.SPUITEM_NOT_FOUND);
        }

        GoodsSpuInfo spuInfo = new GoodsSpuInfo();
        spuInfo.setSpuId(spuItem.getSpuId());
        spuInfo.setSpuName(spuItem.getSpuName());
        spuInfo.setOp(spuItem.getOp());
        spuInfo.setSkuInfos(Maps.uniqueIndex(spuItem.getSkuInfos(), new Function<GoodsSkuInfo, String>(){
            @Override
            public String apply(GoodsSkuInfo goodsSkuInfo) {
                return goodsSkuInfo.getSkuId();
            }
        }));
        List<Map<String, Integer>> shippingInfos = spuItem.getShippingInfos();
        if (shippingInfos != null) {
            Map<String, Integer> shippings = new HashMap<>();
            for (Map<String, Integer> info : shippingInfos) {
                for (Map.Entry<String, Integer> entry : info.entrySet()) {
                    shippings.put(entry.getKey(), entry.getValue());
                }
            }
            spuInfo.setShippingFee(shippings);
        }

        return spuInfo;
    }

    // 从dynamo db获取sku信息
    private GoodsSkuInfo getSkuInfo(GoodsSpuInfo spuInfo, String skuId) {
        String spuId = spuInfo.getSpuId();

        List<GoodsSkuItem> skuItems = dynamoDbService.query(GoodsSkuItem.class, "gid", spuId, "sid", spuId + "-", DynamoDbService.QueryOp.BeginWith, true);
        if (CollectionUtils.isEmpty(skuItems)) {
            logger.error("sku items not found. spuId:{}", spuId);
            throw new ZXException(ResultStatusCode.SKUITEMS_NOT_FOUND);
        }
        if (spuInfo.getSkuInfos() == null || spuInfo.getSkuInfos().size() != skuItems.size()) {
            logger.error("sku data not matched in dynamodb. size in spu={}, size of sku records={}", spuInfo.getSkuInfos().size(), skuItems.size());
            throw new ZXException(ResultStatusCode.GOODS_DATA_ERROR);
        }

        for (GoodsSkuItem skuItem : skuItems) {
            if (skuId.equals(skuItem.getSkuId())) {
                if (spuInfo.getSkuInfos().containsKey(skuId)) {
                    GoodsSkuInfo skuInfo = spuInfo.getSkuInfos().get(skuId);
                    skuInfo.setStockCount(skuItem.getStockCount());
                    skuInfo.setSalesCount(skuItem.getBaseSalesCount() + skuItem.getSalesCount());
                    return skuInfo;
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

    private int calculateTotalFreight(GoodsSpuInfo spuInfo, CreateOrderDirectReqVo orderDirectReqVo) {
        return orderDirectReqVo.getPurchaseCount() *
                spuInfo.calculateFreight(orderDirectReqVo.getProvince(), orderDirectReqVo.getCity());
    }

    private int calculateTotalGoodsPrice(GoodsSkuInfo skuInfo, int purchaseCount) {
        int unitPrice = (int) skuInfo.getPrice() * 100;
        return unitPrice * purchaseCount;
    }

    private int calculateCachBack(GoodsSkuInfo skuInfo, UserAuthEntity authEntity, int purchaseCount) {
        int cashBack = 0;
        int customType = authEntity.getMember();
        if (customType == Member.MERCHANT.code) {
            cashBack = (int) skuInfo.getCashback() * purchaseCount * 100;
        }
        return cashBack;
    }

    private String judgeCashBackedTid(UserAuthEntity authEntity, CreateOrderDirectReqVo reqVo) {
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
