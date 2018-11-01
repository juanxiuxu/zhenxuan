package com.zhenxuan.tradeapi.dao.entity;

import com.zhenxuan.tradeapi.domain.WXPayResultInfo;
import com.zhenxuan.tradeapi.domain.WXUnifiedOrderInfo;

/**
 * 支付通道交易记录
 */
public class PayTradeEntity {

    private String orderId;

    private String authUid;

    private long amount;

    private String prepayId;

    private String ip;

    private String resultCode; // 微信业务结果

    private String transactionId;

    private long totalFee;

    private String feeType;

    private long cashFee;

    private long settlementTotalFee;

    private String isSubscribe;

    private String wxOpenId;

    private String productDesc;

    private String tradeType;

    private String bankType;

    private String paidAt;

    public static PayTradeEntity create(OrderEntity orderEntity, WXUnifiedOrderInfo wxOrderInfo) {
        PayTradeEntity entity = new PayTradeEntity();
        entity.setOrderId(orderEntity.getOid());
        entity.setAuthUid(orderEntity.getAuthUid());
        entity.setAmount(orderEntity.getTotal());
        entity.setPrepayId(wxOrderInfo.getPrepayId());
        entity.setIp(wxOrderInfo.getSpbillCreateIp());

        return entity;
    }

    public static PayTradeEntity create(WXPayResultInfo resultInfo) {
        PayTradeEntity entity = new PayTradeEntity();
        entity.setOrderId(resultInfo.getOrderId());
        entity.setResultCode(resultInfo.getResultCode());
        entity.setTransactionId(resultInfo.getTransactionId());
        entity.setTotalFee(resultInfo.getTotalFee());
        entity.setFeeType(resultInfo.getFeeType());
        entity.setCashFee(resultInfo.getCashFee());
        entity.setSettlementTotalFee(resultInfo.getSettlementTotalFee());
        entity.setIsSubscribe(resultInfo.getIsSubscribe());
        entity.setWxOpenId(resultInfo.getOpenId());
        entity.setProductDesc(resultInfo.getProductDesc());
        entity.setTradeType(resultInfo.getTradeType());
        entity.setBankType(resultInfo.getBankType());
        entity.setPaidAt(resultInfo.getPaidAt());

        return entity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAuthUid() {
        return authUid;
    }

    public void setAuthUid(String authUid) {
        this.authUid = authUid;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
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

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public long getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(long settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
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

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }
}
