package com.zhenxuan.tradeapi.domain;

import com.zhenxuan.tradeapi.common.vo.weixin.WXPayBaseVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayDirectNotifyReqVo;
import com.zhenxuan.tradeapi.utils.JsonUtil;

/**
 * 支付下单的结果模型
 */
public class WXPayResultInfo {

    private String resultCode;

    private String isSubscribe;

    private String openId;

    private String tradeType;

    private String bankType;

    private long totalFee; // int

    private long settlementTotalFee; // int

    private String feeType;

    private long cashFee; // int

    private String cashFeeType;

    private String transactionId;

    private String orderId;

    private String productDesc;

    private String paidAt;

    public static WXPayResultInfo create(WXPayDirectNotifyReqVo notifyReqVo) {
        WXPayResultInfo info = new WXPayResultInfo();

        String resultCode = notifyReqVo.getResultCode();
        info.setResultCode(resultCode);

        if (WXPayBaseVo.WXPayRespCode.SUCCESS.code.equals(resultCode)) {
            info.setResultCode(resultCode);
            info.setTransactionId(notifyReqVo.getTransactionId());
            info.setTotalFee(Long.parseLong(notifyReqVo.getTotalFee()));
            info.setFeeType(notifyReqVo.getFeeType());
            info.setCashFee(Long.parseLong(notifyReqVo.getCashFee()));
            info.setSettlementTotalFee(Long.parseLong(notifyReqVo.getSettlementTotalFee()));
            info.setIsSubscribe(notifyReqVo.getIsSubscribe());
            info.setOpenId(notifyReqVo.getOpenId());
            info.setProductDesc(notifyReqVo.getAttach());
            info.setTradeType(notifyReqVo.getTradeType());
            info.setBankType(notifyReqVo.getBankType());
            info.setPaidAt(notifyReqVo.getTimeEnd());
        }

        return info;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public long getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(long settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public long getCashFee() {
        return cashFee;
    }

    public void setCashFee(long cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
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

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
