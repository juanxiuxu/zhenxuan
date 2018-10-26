package com.zhenxuan.tradeapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * sku直接下单响应类
 */
public class CreateOrderDirectRespVo {

    @JsonProperty("oid")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
