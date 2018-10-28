package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 小程序登陆响应类
 */
public class LoginRespVo {

    private String token;

    @JsonProperty("union_id")
    private boolean hasUnionId;

    @JsonProperty("new_user")
    private int newUser; // 0: false; 1: true

    @JsonProperty("fwh_user")
    private int fwhUser;  // 0: false; 1: true

    // TODO: fe remove
    @JsonProperty("uid")
    private String loginUid;

    @JsonProperty("vip")
    private int member;

    private String iuid;

    private String tid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isHasUnionId() {
        return hasUnionId;
    }

    public void setHasUnionId(boolean hasUnionId) {
        this.hasUnionId = hasUnionId;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public int getFwhUser() {
        return fwhUser;
    }

    public void setFwhUser(int fwhUser) {
        this.fwhUser = fwhUser;
    }

    public String getLoginUid() {
        return loginUid;
    }

    public void setLoginUid(String loginUid) {
        this.loginUid = loginUid;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getIuid() {
        return iuid;
    }

    public void setIuid(String iuid) {
        this.iuid = iuid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
