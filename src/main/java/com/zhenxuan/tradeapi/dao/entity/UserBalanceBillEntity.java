package com.zhenxuan.tradeapi.dao.entity;

import java.util.Date;

/**
 * 用户余额流水记录
 */
public class UserBalanceBillEntity extends Entity {

    private String billId;

    private String oid;

    private String authUid;

    private long amount;

    private int income;  // 1:加 0:减

    private long initBalance;

    private long finalBalance;

    private String currency;

    private int completeStatus;

    private int balanceStatus;

    private int cancelStatus;

    private Date completedAt;

    private int billType;

    private String billDesc;

//    public static UserBalanceBillEntity create() {
//        UserBalanceBillEntity entity = new UserBalanceBillEntity();
//        entity.setCompleteStatus(1); // 1 or 0
//        entity.setBalanceStatus(1); // 1 or 0
//        entity.setCancelStatus(0); // 0
//        entity.setCurrency("CNY");
//
//        return entity;
//    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getBillDesc() {
        return billDesc;
    }

    public void setBillDesc(String billDesc) {
        this.billDesc = billDesc;
    }
}
