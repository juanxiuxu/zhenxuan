package com.zhenxuan.tradeapi.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * 商品sku
 */
@DynamoDBTable(tableName = "zx_p")
public class GoodsSkuItem extends Item {

    @DynamoDBHashKey(attributeName = "gid")
    private String spuId;

    @DynamoDBRangeKey(attributeName = "sid")
    private String skuId;

//    /**
//     * 评价总数
//     */
//    @DynamoDBAttribute(attributeName = "eva_num")
//    private int evaluationNum;
//
//    @DynamoDBAttribute(attributeName = "eva_stars")
//    private int evaluationStars;
//
    /**
     * 预设虚假销量基数
     */
    @DynamoDBAttribute(attributeName = "base_sales")
    private long baseSalesCount;

    /**
     * 真实销量
     */
    @DynamoDBAttribute(attributeName = "sales")
    private long salesCount;

    /**
     * 库存总量
     */
    @DynamoDBAttribute(attributeName = "stock")
    private long stockCount;

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getBaseSalesCount() {
        return baseSalesCount;
    }

    public void setBaseSalesCount(long baseSalesCount) {
        this.baseSalesCount = baseSalesCount;
    }

    public long getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(long salesCount) {
        this.salesCount = salesCount;
    }

    public long getStockCount() {
        return stockCount;
    }

    public void setStockCount(long stockCount) {
        this.stockCount = stockCount;
    }
}
