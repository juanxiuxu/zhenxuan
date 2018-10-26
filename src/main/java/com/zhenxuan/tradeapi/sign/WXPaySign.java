/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.sign;

import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.common.enums.SignMethod;
import com.zhenxuan.tradeapi.utils.CommonUtil;
import com.zhenxuan.tradeapi.vo.weixin.WXPayUnifiedOrderReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Weixin sign method.
 * 微信支付签名方法
 */
@Component("wxPaySign")
public class WXPaySign extends Sign {

    private static final Logger logger = LoggerFactory.getLogger(WXPaySign.class);

    @Resource(name = "signKeyMap")
    private Map<String, String> signKeyMap;

    public void setSignKeyMap(Map<String, String> signKeyMap) {
        this.signKeyMap = signKeyMap;
    }

    @Override
    protected String buildSignImpl(SignConfig config, Map<String, Object> data) {
//        StringBuilder res = new StringBuilder();
//
//        // sort the param key in ascending order
//        Map<String, Object> sortedMap = new TreeMap<String, Object>(map);
//
//        // sign should not be included in sign method
//        sortedMap.remove("sign");
//
//        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
//            // convert entry to "key=value" string
//            if (entry.getValue() != null) {
//                res.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
//            }
//        }
//        res.append("key").append("=").append(signKeyMap.get(config.keyRefStr));
//        logger.info("wx pay check sign with str: {}", res);
//
//        return CommonUtil.md5Digest(res.toString(), config.charset).toUpperCase();


        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(FIELD_SIGN)) {
                continue;
            }
            if (data.get(k) instanceof String) {
                String value = (String)data.get(k);
                if (value.trim().length() > 0) {// 参数值为空，则不参与签名
                    sb.append(k).append("=").append(value.trim()).append("&");
                }
            }
        }
        sb.append("key=").append(config.keyRefStr);
        if (config.signMethod.code == SignMethod.MD5.code) {
            return CommonUtil.md5Digest(sb.toString(), config.charset).toUpperCase();
        } else if (config.signMethod.code == SignMethod.HMACSHA256.code) {
            try {
                return CommonUtil.hmacSHA256Digest(sb.toString(), config.keyRefStr, config.charset).toUpperCase();
            } catch (Exception ex) {
                logger.error("HMACSHA256 digest error. {}", Throwables.getStackTraceAsString(ex));
                throw new RuntimeException("HMACSHA256 digest error");
            }
        } else {
            logger.error("This sign method:{} not support for WXPay sign", config.signMethod);
            throw new RuntimeException("Not supported sign method");
        }
    }

    public static void main(String[] args) {

        WXPaySign wxPaySign = new WXPaySign();
        wxPaySign.setSignKeyMap(new HashMap<>());

        WXPayUnifiedOrderReqVo payReqVo = new WXPayUnifiedOrderReqVo();
        payReqVo.setAppId("myappid");
        payReqVo.setMerchantId("mymerchantid");
        payReqVo.setNonceStr(UUID.randomUUID().toString());
        payReqVo.setBody("爱真选");

        System.out.println(wxPaySign.buildSign(SignConfig.WX_PAY_SIGN, payReqVo));
    }
}
