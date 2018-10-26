package com.zhenxuan.tradeapi.vo.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信支付查询订单请求类
 */
public class WXPayQueryOrderReqVo {

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mch_id")
    private String merchantId;

    @JsonProperty("transaction_id")
    private String transactionId;  // 二选一

    @JsonProperty("out_trade_no")
    private String orderId;   // 二选一

    @JsonProperty("nonce_str")
    private String nonceStr;

    private String sign;

    @JsonProperty("sign_type")
    private String signType;
}
