package com.zhenxuan.tradeapi.entity;

import com.zhenxuan.tradeapi.utils.JsonUtil;

/**
 * mysql数据项基类
 */
public class Entity {

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
