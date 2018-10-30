package com.zhenxuan.tradeapi.dao.entity;

import com.zhenxuan.tradeapi.domain.WXUnifiedOrderInfo;
import com.zhenxuan.tradeapi.utils.GlobalIdUtil;

import java.util.Date;

/**
 * 支付通道交易记录
 */
public class PayTradeEntity {

    private String payTradeId;

    private String orderId;

    private String authUid;

    private long amount;

    private String prepayId;

    private String ip;

    private String resultCode; // 微信业务结果

    private String transactionId;

    private int totalFee;

    private String feeType;

    private int cashFee;

    private int settlementTotalFee;

    private String isSubscribe;

    private String wxOpenId;

    private String productDesc;

    private String tradeType;

    private String bankType;

    private Date paidAt;

    public static PayTradeEntity create(OrderEntity orderEntity, WXUnifiedOrderInfo wxOrderInfo) {
        PayTradeEntity entity = new PayTradeEntity();
        entity.setPayTradeId(GlobalIdUtil.newOrderId());
        entity.setOrderId(orderEntity.getOid());
        entity.setAuthUid(orderEntity.getAuthUid());
        entity.setAmount(orderEntity.getTotal());
        entity.setPrepayId(wxOrderInfo.getPrepayId());
        entity.setIp(wxOrderInfo.getSpbillCreateIp());

        return entity;
    }

    public String getPayTradeId() {
        return payTradeId;
    }

    public void setPayTradeId(String payTradeId) {
        this.payTradeId = payTradeId;
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

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
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

    public int getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(int settlementTotalFee) {
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

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }
}
