package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 需要校验authUid Token的请求基类
 */
public class BaseAuthReqVo implements Input {

    /**
     * authUid
     */
    @JsonProperty("auth_uid")
    private String authUid;

    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }
}
