package com.zhenxuan.tradeapi.vo.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhenxuan.tradeapi.utils.JsonUtil;

/**
 * 微信统一下单响应类
 */
public class WXPayUnifiedOrderRespVo extends BaseWXPayVo {

    // 以下字段在return_code为SUCCESS的时候有返回
    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mch_id")
    private String merchantId;

    @JsonProperty("device_info")
    private String deviceInfo;

    @JsonProperty("nonce_str")
    private String nonceStr;

    private String sign;

    @JsonProperty("result_code")
    private String resultCode;

    @JsonProperty("err_code")
    private String errCode;

    @JsonProperty("err_code_des")
    private String errCodeDesc;

    // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
    @JsonProperty("trade_type")
    private String tradeType;

    @JsonProperty("prepay_id")
    private String prepayId;

    @JsonProperty("code_url")
    private String codeUrl;


    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
