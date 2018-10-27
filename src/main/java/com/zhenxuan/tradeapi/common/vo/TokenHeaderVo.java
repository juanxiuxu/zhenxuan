package com.zhenxuan.tradeapi.common.vo;

/**
 * token组成类
 */
public class TokenHeaderVo {

    private String loginUid;

    private String authUid;

    private String avatar;

    private String userName;

    public TokenHeaderVo() {
    }

    public TokenHeaderVo(String loginUid, String authUid, String avatar, String userName) {
        this.loginUid = loginUid;
        this.authUid = authUid;
        this.avatar = avatar;
        this.userName = userName;
    }

    public String getLoginUid() {
        return loginUid;
    }

    public void setLoginUid(String loginUid) {
        this.loginUid = loginUid;
    }

    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
