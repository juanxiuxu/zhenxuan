package com.zhenxuan.tradeapi.entity;

import java.util.Date;

/**
 * mysql 用户登陆表
 */
public class UserLoginEntity extends Entity {

    private String loginUid;

    private String wxAppType;

    private String wxOpenId;

    private long balance;  // 兼容未认证但还有余额的用户

    private String unionId;

    private String fwhOpenId; // 服务号

    private String tid; // 进店的店主id

    private String iuid; // 引荐人id

    private String wxSessionKey;

    private Date lastLogin;

//    public UserLoginEntity from(LoginReqVo loginReqVo, UserWXInfoVo userWXInfoVo) {
//        this.wxAppType = loginReqVo.getAppType();
//        this.wxOpenId = userWXInfoVo.getOpenid();
//        if (!"".equals(userWXInfoVo.getUnionId())) {
//            this.unionId = userWXInfoVo.getUnionId();
//        }
//        this.wxSessionKey = userWXInfoVo.getSessionKey();
//        return this;
//    }

    public UserLoginEntity copyFrom(UserLoginEntity from) {
        if (from == null) {
            return null;
        }

        this.setLoginUid(from.getLoginUid());
        this.setWxAppType(from.getWxAppType());
        this.setWxOpenId(from.getWxOpenId());
        this.setBalance(from.getBalance());
        this.setUnionId(from.getUnionId());
        this.setFwhOpenId(from.getFwhOpenId());
        this.setTid(from.getTid());
        this.setIuid(from.getIuid());
        this.setWxSessionKey(from.getWxSessionKey());
        this.setLastLogin(from.getLastLogin());

        return this;
    }

    public String getLoginUid() {
        return loginUid;
    }

    public void setLoginUid(String loginUid) {
        this.loginUid = loginUid;
    }

    public String getWxAppType() {
        return wxAppType;
    }

    public void setWxAppType(String wxAppType) {
        this.wxAppType = wxAppType;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getFwhOpenId() {
        return fwhOpenId;
    }

    public void setFwhOpenId(String fwhOpenId) {
        this.fwhOpenId = fwhOpenId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getIuid() {
        return iuid;
    }

    public void setIuid(String iuid) {
        this.iuid = iuid;
    }

    public String getWxSessionKey() {
        return wxSessionKey;
    }

    public void setWxSessionKey(String wxSessionKey) {
        this.wxSessionKey = wxSessionKey;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
