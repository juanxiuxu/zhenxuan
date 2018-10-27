package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 小程序用户登陆请求类
 */
public class LoginReqVo implements Input {

    @NotBlank
    private String code;

    @NotBlank
    @JsonProperty("app_type")
    private String appType; // 区分不同小程序

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
