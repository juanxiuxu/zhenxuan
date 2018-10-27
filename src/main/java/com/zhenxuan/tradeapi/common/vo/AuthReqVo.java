package com.zhenxuan.tradeapi.common.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 小程序用户授权请求类
 */
public class AuthReqVo extends BaseLoginReqVo implements Input {

    @NotBlank
    private String iv;

    @NotBlank
    private String encryptedData;

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
