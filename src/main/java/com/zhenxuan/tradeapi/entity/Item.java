package com.zhenxuan.tradeapi.entity;

import com.zhenxuan.tradeapi.utils.JsonUtil;

/**
 * dynamodb数据项基类
 */
public class Item {

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
