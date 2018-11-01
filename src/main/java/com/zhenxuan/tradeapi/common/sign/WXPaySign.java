/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.common.sign;

import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayUnifiedOrderReqVo;
import com.zhenxuan.tradeapi.utils.CommonUtil;
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
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(FIELD_SIGN) || data.get(k) == null) {
                continue;
            }
            if (!(data.get(k) instanceof String)) {
                logger.error("data value is not an instance of String. key:{}", k);
            }
            String value = ((String) data.get(k)).trim();
            if (value.length() > 0) {// 参数值为空，则不参与签名
                sb.append(k).append("=").append(value).append("&");
            }
        }
        sb.append("key=").append(signKeyMap.get(config.keyRefStr));
        logger.debug("sign sb: [{}]", sb.toString());
        try {
            if (config.signMethod.MD5.equals(config.signMethod)) {
                return CommonUtil.md5Digest(sb.toString(), config.charset).toUpperCase();
            } else if (config.signMethod.HMACSHA256.equals(config.signMethod)) {
                return CommonUtil.hmacSHA256Digest(sb.toString(), signKeyMap.get(config.keyRefStr), config.charset);
            } else {
                logger.error("Not supported sign type:{} for wxPay", config.signMethod);
                return null;
            }
        } catch (Exception e) {
            logger.error("sign for wxPay error:{}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    public static void main(String[] args) {

        WXPaySign wxPaySign = new WXPaySign();
        wxPaySign.setSignKeyMap(new HashMap<String, String>(){{
            put("wx.pay.sign.key", "957bca0025845050fbbe8f5ec93c8938");
        }});

        WXPayUnifiedOrderReqVo payReqVo = new WXPayUnifiedOrderReqVo();
        payReqVo.setAppId("wxe93e5b174885a5c0");
        payReqVo.setMerchantId("1507569651");
        payReqVo.setDeviceInfo("");
        payReqVo.setNonceStr("wHJYaXeSM0egNVzn9XLR3jqa2XIP7KUK");
        payReqVo.setFeeType("CNY");
        payReqVo.setTotalFee("1");
        payReqVo.setSpbillCreateIp("123.12.12.123");
        payReqVo.setNotifyUrl("http://www.example.com/wxpay/notify");
        payReqVo.setTradeType("NATIVE");
        payReqVo.setSkuId("12");
        payReqVo.setBody("腾讯充值中心-QQ会员充值");
        payReqVo.setOrderId("2016090910595900000012");
        payReqVo.setSignType(SignConfig.WX_PAY_SIGN.signMethod.des);

        String sign = wxPaySign.buildSign(SignConfig.WX_PAY_SIGN, payReqVo);
        if (!"66A25DF8E63CE03F9F4D0AC3A3258C98DAE711F416147D5CBBC49D20E48F0694".equals(sign)) {
            System.out.printf("sign error: %s\n", sign);
        }
        System.out.println("sign success");
    }
}
