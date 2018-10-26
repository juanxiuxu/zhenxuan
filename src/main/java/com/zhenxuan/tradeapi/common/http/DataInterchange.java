/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.common.http;

/**
 * Created by IntelliJ IDEA.
 * 交换数据格式
 * @author tianyu07.
 * @date 16/6/30 20:44.
 */
public enum DataInterchange {
    XML(1, "xml"),
    JSON(2, "json");

    public final int code;
    public final String des;

    DataInterchange(int code, String des) {
        this.code = code;
        this.des = des;
    }
}
