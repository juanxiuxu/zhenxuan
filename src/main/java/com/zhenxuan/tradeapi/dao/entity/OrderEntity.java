package com.zhenxuan.tradeapi.dao.entity;

import java.util.Date;

/**
 * 订单记录
 */
public class OrderEntity extends Entity {

    private String oid;

    private String authUid;

    private String tid;

    private String iuid;

    private int vip;

    private int orderStatus;

    private String code;

    private String op;

    private String recheck;

    private int total;

    private int goodsTotal;

    private int cashback;

    private int bonus;

    private String couponCode;

    private int couponDiscount;

    private int freight;

    private int freightDiscount;

    private int payment;

    private int userBalanceConsume;

    private int discount;

    private int actualPayment;

    private Date paidAt;

    private String payOrd;

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

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getRecheck() {
        return recheck;
    }

    public void setRecheck(String check) {
        this.recheck = check;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(int goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public int getCashback() {
        return cashback;
    }

    public void setCashback(int cashback) {
        this.cashback = cashback;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(int couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public int getFreightDiscount() {
        return freightDiscount;
    }

    public void setFreightDiscount(int freightDiscount) {
        this.freightDiscount = freightDiscount;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getUserBalanceConsume() {
        return userBalanceConsume;
    }

    public void setUserBalanceConsume(int userBalanceConsume) {
        this.userBalanceConsume = userBalanceConsume;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(int actualPayment) {
        this.actualPayment = actualPayment;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public String getPayOrd() {
        return payOrd;
    }

    public void setPayOrd(String payOrd) {
        this.payOrd = payOrd;
    }

}
