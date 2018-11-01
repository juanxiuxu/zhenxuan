package com.zhenxuan.tradeapi.dao.entity;

import java.util.Date;

/**
 * 用户余额流水记录
 */
public class UserBalanceBillEntity extends Entity {

    private String billId;

    private String authUid;

    private long amount;

    private int income;

    private long initBalance;

    private long finalBalance;

    private String currency;

    private int completeStatus;

    private int balanceStatus;

    private int cancelStatus;

    private Date completeAt;

    private int type;

    private String orderId;

    private String desc;

    public static UserBalanceBillEntity create() {
        UserBalanceBillEntity entity = new UserBalanceBillEntity();
        entity.setCompleteStatus(1); // 1 or 0
        entity.setBalanceStatus(1); // 1 or 0
        entity.setCancelStatus(0); // 0
        entity.setCurrency("CNY");

        return entity;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
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

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public long getInitBalance() {
        return initBalance;
    }

    public void setInitBalance(long initBalance) {
        this.initBalance = initBalance;
    }

    public long getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(long finalBalance) {
        this.finalBalance = finalBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(int completeStatus) {
        this.completeStatus = completeStatus;
    }

    public int getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(int balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public Date getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(Date completeAt) {
        this.completeAt = completeAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
