package com.zhenxuan.tradeapi.vo.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信支付请求和响应基类
 */
public class BaseWXPayVo {

    @JsonProperty("return_code")
    private String code;

    @JsonProperty("return_msg")
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public enum WXPayRespCode {

        SUCCESS("SUCCESS"),
        FAIL("FAIL");

        WXPayRespCode(String code) {
            this.code = code;
        }

        public final String code;
    }
}
