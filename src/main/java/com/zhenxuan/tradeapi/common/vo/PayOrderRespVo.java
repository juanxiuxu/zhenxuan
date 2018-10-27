package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 订单支付响应类
 */
public class PayOrderRespVo {

    private Payload payload;

    @JsonProperty("all_balance")
    private boolean allBalance;

    public static class Payload {

        private String appId;

        private long timeStamp;
    }
}
