package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * sku直接下单请求类
 */
public class CreateOrderDirectReqVo extends BaseAuthReqVo {

    @NotBlank
    @JsonProperty("sku")
    private String skuId;

    @NotBlank
    private String address;

    @NotBlank
    private String contact;

    @NotBlank
    private String phone;

    @NotBlank
    private String province;

    @NotBlank
    private String city;

    @NotBlank
    @JsonProperty("county")
    private String country;

    /**
     * 购买数量
     */
    @NotBlank
    @JsonProperty("num")
    private int purchaseCount;

    /**
     * 优惠券码
     */
    @JsonProperty("coupon_code")
    private String couponCode;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchseCount) {
        this.purchaseCount = purchseCount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
