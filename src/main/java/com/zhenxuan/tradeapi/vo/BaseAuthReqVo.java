package com.zhenxuan.tradeapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 需要校验authUid Token的请求基类
 */
public class BaseAuthReqVo implements Input {

    /**
     * authUid
     */
    @NotBlank(message = "should not be null or empty")
    @JsonProperty("auth_uid")
    private String authUid;

    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }
}
