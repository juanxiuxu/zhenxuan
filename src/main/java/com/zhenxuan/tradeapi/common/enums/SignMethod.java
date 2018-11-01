/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.common.enums;

/**
 * Created by IntelliJ IDEA.
 * 签名方法
 * @author tianyu07.
 * @date 16/6/30 20:41.
 */
public enum SignMethod {

    MD5(1, "MD5"),
    SHA_1(2, "SHA-1"),
    HMACSHA256(3, "HMAC-SHA256");

    public final int code;
    public final String des;

    SignMethod(int code, String des) {
        this.code = code;
        this.des = des;
    }
}
