/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.sign;

import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.utils.CommonUtil;
import com.zhenxuan.tradeapi.utils.JsonUtil;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * 签名类
 */
public abstract class Sign {

    protected static final String FIELD_SIGN = "sign";

    /**
     * 构建签名 by object
     * @param config
     * @param object
     * @return
     */
    public String buildSign(SignConfig config, Object object) {
        Map<String, Object> responseMap = JsonUtil.convert(object, Map.class);
        Map<String, Object> signMap = CommonUtil.convertParam2LowerScore(responseMap);

        return buildSign(config, signMap);
    }

    /**
     * 构建签名 by map
     * @param config
     * @param params
     * @return
     */
    public String buildSign(SignConfig config, Map<String, Object> params) {
        return buildSign(config, params);
    }

    /**
     * 校验签名 by map
     * @param signConfig
     * @param signParams
     * @param sign
     * @return
     */
    public boolean checkSign(SignConfig signConfig, Map<String, Object> signParams, String sign) {
        String expectedSign = buildSign(signConfig, signParams);
        return sign.equals(expectedSign);
    }

    /**
     * 校验签名 by object
     * @param signConfig
     * @param signObj
     * @param sign
     * @return
     */
    public boolean checkSign(SignConfig signConfig, Object signObj, String sign) {
        String expectedSign = buildSign(signConfig, signObj);
        return sign.equals(expectedSign);
    }

    /**
     * 生成签名
     * @param config
     * @param params
     * @return
     */
    protected abstract String buildSignImpl(SignConfig config, Map<String, Object> params);

}
