package com.zhenxuan.tradeapi.dao.entity;

import com.zhenxuan.tradeapi.common.vo.weixin.WXGetUserInfoRespVo;

/**
 * mysql 用户认证表
 */
public class UserAuthEntity extends Entity {

    private String authUid;

    private String unionId;

    private String wxAppType;

    private String wxOpenId;

    private long balance;

    private int member; //

    private long expired; //member expiration

    private String fwhOpenId; // 服务号

    private String tid; // 进店的店主id

    private String iuid; // 引荐人id

    private String cardId;

    private String bankName;

    private String bankCode;

    private String realName;

    private String phone;

    private String avatar;

    private String nickName;

    private int gender;

    public UserAuthEntity() {
    }

    public UserAuthEntity(WXGetUserInfoRespVo respVo) {
        this.wxOpenId = respVo.getOpenId();
        this.nickName = respVo.getNickName();
        this.gender = respVo.getGender();
        this.avatar = respVo.getAvatarUrl();
        this.unionId = respVo.getUnionId();
    }

    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isMemberExpired() {
        if (expired == -1 || expired == 0 || expired > System.currentTimeMillis()) {
            return false;
        } else {
            return true;
        }
    }
}
