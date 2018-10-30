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

        // sprivate long timeStamp;

        @JsonProperty("prepay_id")
        private String prepayId;

        public Payload() {}

        public Payload(String appId, String prepayId) {
            this.appId = appId;
            this.prepayId = prepayId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public boolean isAllBalance() {
        return allBalance;
    }

    public void setAllBalance(boolean allBalance) {
        this.allBalance = allBalance;
    }
}
