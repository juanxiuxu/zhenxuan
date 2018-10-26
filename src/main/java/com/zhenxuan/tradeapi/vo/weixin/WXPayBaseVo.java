package com.zhenxuan.tradeapi.vo.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信支付请求和响应基类
 */
public class WXPayBaseVo {

    @JsonProperty("return_code")
    private String returnCode;

    @JsonProperty("return_msg")
    private String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
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
