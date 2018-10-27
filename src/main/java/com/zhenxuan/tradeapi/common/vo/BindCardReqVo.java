package com.zhenxuan.tradeapi.common.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 绑卡请求类
 */
public class BindCardReqVo extends BaseAuthReqVo {

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @NotBlank
    private String cardId;

    @NotBlank
    private String bankName;

    @NotBlank
    private String bankCode;
}
