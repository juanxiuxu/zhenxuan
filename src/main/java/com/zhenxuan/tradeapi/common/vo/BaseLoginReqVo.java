package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 需要校验loginUid Token的请求基类
 */
public class BaseLoginReqVo implements Input {

    @NotBlank(message = "should not be null or empty")
    @JsonProperty("login_uid")
    private String loginUid;

    public String getLoginUid() {
        return loginUid;
    }

    public void setLoginUid(String loginUid) {
        this.loginUid = loginUid;
    }
}
