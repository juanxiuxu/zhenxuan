package com.zhenxuan.tradeapi.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 进店请求类
 */
public class EnterShopReqVo extends BaseAuthReqVo {

    @NotBlank
    private String tid;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
