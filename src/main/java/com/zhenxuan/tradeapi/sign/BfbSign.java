/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.sign;

import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.TreeMap;

/**
 * BaiFuBao sign method.
 * 百付宝签名方法
 *
 * @author tianyu07
 * @author leimingshan
 * @date 2016/6/29
 */
@Component("bfbSign")
public class BfbSign extends Sign {

    private static final Logger logger = LoggerFactory.getLogger(BfbSign.class);

    @Resource(name = "signKeyMap")
    private Map<String, String> signKeyMap;

    @Override
    protected String buildSignImpl(SignConfig config, Map<String, Object> map) {
        StringBuilder res = new StringBuilder();

        // sort the param key in ascending order
        Map<String, Object> sortedMap = new TreeMap<String, Object>(map);

        // sign should not be included in sign method
        sortedMap.remove("sign");

        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            // convert entry to "key=value" string
            if (entry.getValue() != null) {
                res.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
            }
        }
        res.append("key").append("=").append(signKeyMap.get(config.keyRefStr));
        logger.info("check sign with str: {}", res);

        return CommonUtil.md5Digest(res.toString(), config.charset);
    }
}
