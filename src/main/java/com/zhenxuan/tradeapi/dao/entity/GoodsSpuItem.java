package com.zhenxuan.tradeapi.dao.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.zhenxuan.tradeapi.domain.GoodsSkuInfo;
import com.zhenxuan.tradeapi.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * 商品spu
 */
@DynamoDBTable(tableName = "zx_p")
public class GoodsSpuItem extends Item {

    @DynamoDBHashKey(attributeName = "gid")
    private String spuId;

    @DynamoDBRangeKey(attributeName = "sid")
    private String skuId;

    @DynamoDBAttribute(attributeName = "name")
    private String spuName;

    @DynamoDBAttribute(attributeName = "op")
    private String op;

//    @DynamoDBAttribute(attributeName = "cid")
//    private List<String> cids;

//    /**
//     * 商品详情顶部录轮播
//     */
//    @DynamoDBAttribute(attributeName = "carousel_image")
//    private List<String> carouselImages;
//
//    /**
//     * 商品详情图
//     */
//    @DynamoDBAttribute(attributeName = "default_image")
//    private List<String> defaultImages;
//
//    /**
//     * 相关商品spu列表
//     */
//    @DynamoDBAttribute(attributeName = "correlation")
//    private List<String> correlations;

    /**
     * 商品描述
     */
    @DynamoDBAttribute(attributeName = "describe")
    private String description;

//    /**
//     * 无用
//     */
//    @DynamoDBAttribute(attributeName = "direct")
//    private boolean direct;

    /**
     * 打折价格
     */
    @DynamoDBAttribute(attributeName = "discount_price")
    private int discountPrice;

    /**
     * 返现
     */
    @DynamoDBAttribute(attributeName = "goods_cashback")
    private int goodsCashback;

    @DynamoDBAttribute(attributeName = "goods_price")
    private int goodsPrice;

//    /**
//     *  商品最低价格，显示用
//     */
//    @DynamoDBAttribute(attributeName = "min_price")
//    private int minGoodsPrice;

    @DynamoDBAttribute(attributeName = "skus")
    @DynamoDBTypeConverted(converter = SkuInfoListConverter.class)
    private List<GoodsSkuInfo> skuInfos;

    public static class SkuInfoListConverter implements DynamoDBTypeConverter<String, List<GoodsSkuInfo> > {
        @Override
        public String convert(List<GoodsSkuInfo> skuInfos) {
            return JsonUtil.toString(skuInfos);
        }

        @Override
        public List<GoodsSkuInfo> unconvert(String data) {
            return (List<GoodsSkuInfo>)JsonUtil.toObject(data, List.class);
        }
    }

    /**
     * 支持的地区和运费
     */
    @DynamoDBAttribute(attributeName = "shipping")
    @DynamoDBTypeConverted(converter = ShippingInfosConverter.class)
    private List<Map<String, Integer>> shippingInfos;

    public static class ShippingInfosConverter implements DynamoDBTypeConverter<String, List<Map<String, Integer>>> {
        @Override
        public String convert(List<Map<String, Integer>> shippingInfos) {
            return JsonUtil.toString(shippingInfos);
        }

        @Override
        public List<Map<String, Integer>> unconvert(String data) {
            return JsonUtil.toObject(data, List.class);
        }
    }

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

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getGoodsCashback() {
        return goodsCashback;
    }

    public void setGoodsCashback(int goodsCashback) {
        this.goodsCashback = goodsCashback;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public List<GoodsSkuInfo> getSkuInfos() {
        return skuInfos;
    }

    public void setSkuInfos(List<GoodsSkuInfo> skuInfos) {
        this.skuInfos = skuInfos;
    }

    public List<Map<String, Integer>> getShippingInfos() {
        return shippingInfos;
    }

    public void setShippingInfos(List<Map<String, Integer>> shippingInfos) {
        this.shippingInfos = shippingInfos;
    }
}
