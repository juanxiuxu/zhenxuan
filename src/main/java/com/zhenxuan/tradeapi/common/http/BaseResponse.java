/*
 * Copyright © 2010 www.myctu.cn. All rights reserved.
 */
/**
 * project : ctu-utils
 * user created : pippo
 * date created : 2013-4-17 - 下午12:11:51
 */
package com.zhenxuan.tradeapi.common.http;

/**
 * Created by pippo on 15/8/23.
 */
public class BaseResponse {

    public BaseResponse() {
        this(true);
    }

    public BaseResponse(boolean success) {
        this(success, null);
    }

    public BaseResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public final boolean success;
    public final String error;

    @Override
    public String toString() {
        return String.format("BaseResponse{'success'=%s, 'error'=%s}", success, error);
    }
}
