package com.zhenxuan.tradeapi.vo;

import org.hibernate.validator.constraints.NotBlank;

public class BeInvitedReqVo extends BaseAuthReqVo {

    @NotBlank
    private String iuid;

    public String getIuid() {
        return iuid;
    }

    public void setIuid(String iuid) {
        this.iuid = iuid;
    }
}
