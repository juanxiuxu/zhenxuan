package com.zhenxuan.tradeapi.common.vo.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信登陆认证响应类
 */
public class WXCode2SessionRespVo {

    @JsonProperty("openid")
    private String openId;    //用户唯一标识

    @JsonProperty("session_key")
    private String sessionKey;  // 会话密钥

    @JsonProperty("unionid")
    private String unionId;    // 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。

    @JsonProperty("errcode")
    private int errCode;    // 错误码

    @JsonProperty("errmsg")
    private String errMsg;    //错误信息

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openid) {
        this.openId = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return String.format("errcode=%d, errmsg=%s, unionid=%s, openid=%s, session_key=%s",
                errCode, errMsg, unionId, openId, sessionKey);
    }
}
