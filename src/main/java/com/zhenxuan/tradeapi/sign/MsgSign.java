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
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Message sign method.
 *
 * @author yangyang07
 * @since 16/7/5
 */
@Component("msgSign")
public class MsgSign extends Sign {

    private static Logger logger = LoggerFactory.getLogger(MsgSign.class);

    @Resource(name = "signKeyMap")
    private Map<String, String> signKeyMap;

    @Override
    protected String buildSignImpl(SignConfig config, Map<String, Object> map) {
        StringBuilder res = new StringBuilder();
        // 入参按照升序排列
        Map<String, Object> sortedMap = new TreeMap<String, Object>(map);

        // sign不参与签名生成
        sortedMap.remove("sign");

        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            // convert entry to "key=value" string
            if (entry.getValue() != null) {
                res.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
        }

        try {
            String encodeString = URLEncoder.encode(res.toString(), config.charset);
            String resultString = encodeString + signKeyMap.get(config.keyRefStr);

            return CommonUtil.md5Digest(resultString, config.charset);

        } catch (Exception e) {
            logger.error("encode msg sign error");
            return null;
        }
    }
}
