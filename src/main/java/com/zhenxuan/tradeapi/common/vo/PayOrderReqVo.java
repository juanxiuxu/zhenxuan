package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 订单支付请求类
 */
public class PayOrderReqVo extends BaseAuthReqVo {

    @JsonProperty("oid")
    private String orderId;

    @JsonProperty("use_balance")
    private boolean isUseBalance;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isUseBalance() {
        return isUseBalance;
    }

    public void setUseBalance(boolean useBalance) {
        isUseBalance = useBalance;
    }
}
