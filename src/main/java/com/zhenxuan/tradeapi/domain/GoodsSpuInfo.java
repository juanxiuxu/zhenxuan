package com.zhenxuan.tradeapi.domain;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GoodsSpuInfo {

    private static final Logger logger = LoggerFactory.getLogger(GoodsSpuInfo.class);

    private String SpuId;

    private String SpuName;

    private String op;

    private Map<String, GoodsSkuInfo> skuInfos;

    private Map<String, Integer> shippingFee;

    public String getSpuId() {
        return SpuId;
    }

    public void setSpuId(String spuId) {
        SpuId = spuId;
    }

    public String getSpuName() {
        return SpuName;
    }

    public void setSpuName(String spuName) {
        SpuName = spuName;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Map<String, GoodsSkuInfo> getSkuInfos() {
        return skuInfos;
    }

    public void setSkuInfos(Map<String, GoodsSkuInfo> skuInfos) {
        this.skuInfos = skuInfos;
    }

    public Map<String, Integer> getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Map<String, Integer> shippingFee) {
        this.shippingFee = shippingFee;
    }

    public int calculateFreight(String province, String city) {
        int freight = 0;

        String key = String.format("%s-%s", province, city);
        if (shippingFee.containsKey(key)) {
            freight = shippingFee.get(key);
            if (freight < 0) {
                logger.info("The shipment is out of delivery area");
                throw new ZXException(ResultStatusCode.OUT_OF_DELIVERY_AREA);
            }
            return freight;
        }

        if (shippingFee.containsKey(province)) {
            freight = shippingFee.get(province);
            if (freight < 0) {
                logger.info("The shipment is out of delivery area");
                throw new ZXException(ResultStatusCode.OUT_OF_DELIVERY_AREA);
            }
            return freight;
        }

        return freight;
    }
}
