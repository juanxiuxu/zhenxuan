/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.common.enums;

/**
 * Sign configuration for settlement.
 *
 * @author tianyu07.
 * @date 16/6/29 11:49.
 */
public enum SignConfig {

    WX_PAY_SIGN("utf-8", SignMethod.HMACSHA256, "wx.pay.sign.key"),
    MSG_SIGN("utf-8", SignMethod.MD5, "message.sign.key");

    public final String charset;
    public final SignMethod signMethod;
    public final String keyRefStr;

    SignConfig(String charset, SignMethod signMethod, String keyRefStr) {
        this.charset = charset;
        this.signMethod = signMethod;
        this.keyRefStr = keyRefStr;
    }
}
