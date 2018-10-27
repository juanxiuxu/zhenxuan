package com.zhenxuan.tradeapi.common.vo.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信支付查询订单响应类
 */
public class WXPayQueryOrderRespVo extends WXPayBaseVo {


    // 以下字段在return_code为SUCCESS的时候有返回
    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mch_id")
    private String merchantId;

    @JsonProperty("nonce_str")
    private String nonceStr;

    private String sign;

    @JsonProperty("result_code")
    private String resultCode;

    @JsonProperty("err_code")
    private String errCode;

    @JsonProperty("err_code_des")
    private String errCodeDesc;

    // 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。
    @JsonProperty("device_info")
    private String deviceInfo;

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("is_subscribe")
    private String isSubscribe;

    @JsonProperty("trade_type")
    private String tradeType;

    @JsonProperty("trade_status")
    private String tradeStatus;

    @JsonProperty("trade_status_desc")
    private String tradeStatusDesc;

    @JsonProperty("bank_type")
    private String bankType;

    @JsonProperty("total_fee")
    private int totalFee;

    @JsonProperty("settlement_total_fee")
    private int settlementTotalFee;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("cash_fee")
    private int cashFee;

    @JsonProperty("cash_fee_type")
    private String cashFeeType;

    @JsonProperty("coupon_fee")
    private int couponFee;

    @JsonProperty("coupon_count")
    private int couponCount;

    @JsonProperty("coupon_type_$n")
    private String couponType$n;

    @JsonProperty("coupon_id_$n")
    private String couponId$n;

    @JsonProperty("coupon_fee_$n")
    private String couponFee$n;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("out_trade_no")
    private String orderId;

    @JsonProperty("attach")
    private String attach;

    @JsonProperty("time_end")
    private String timeEnd;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDesc() {
        return errCodeDesc;
    }

    public void setErrCodeDesc(String errCodeDesc) {
        this.errCodeDesc = errCodeDesc;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeStatusDesc() {
        return tradeStatusDesc;
    }

    public void setTradeStatusDesc(String tradeStatusDesc) {
        this.tradeStatusDesc = tradeStatusDesc;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(int settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public int getCashFee() {
        return cashFee;
    }

    public void setCashFee(int cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public int getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(int couponFee) {
        this.couponFee = couponFee;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public String getCouponType$n() {
        return couponType$n;
    }

    public void setCouponType$n(String couponType$n) {
        this.couponType$n = couponType$n;
    }

    public String getCouponId$n() {
        return couponId$n;
    }

    public void setCouponId$n(String couponId$n) {
        this.couponId$n = couponId$n;
    }

    public String getCouponFee$n() {
        return couponFee$n;
    }

    public void setCouponFee$n(String couponFee$n) {
        this.couponFee$n = couponFee$n;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
