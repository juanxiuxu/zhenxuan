package com.zhenxuan.tradeapi.domain;

import com.zhenxuan.tradeapi.vo.weixin.WXCode2SessionRespVo;

/**
 * 用户微信基本信息
 */
public class UserWXInfo {

    private String openid;

    private String sessionKey;

    private String unionId;

    public UserWXInfo(WXCode2SessionRespVo respVo) {
        this.openid = respVo.getOpenId();
        this.sessionKey = respVo.getSessionKey();
        this.unionId = respVo.getUnionId();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
