package com.zhenxuan.tradeapi.domain;

public class WXUnifiedOrderInfo {

    private String appId;

    private String prepayId;

    private String spbillCreateIp;

    public WXUnifiedOrderInfo(String appId, String prepayId, String spbillCreateIp) {
        this.appId = appId;
        this.prepayId = prepayId;
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
}
