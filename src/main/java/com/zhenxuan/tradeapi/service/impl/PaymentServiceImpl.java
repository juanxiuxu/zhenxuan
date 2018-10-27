package com.zhenxuan.tradeapi.service.impl;

import com.zhenxuan.tradeapi.service.PaymentService;
import com.zhenxuan.tradeapi.thirdparty.WXPayUnifiedOrder;
import com.zhenxuan.tradeapi.common.vo.PayOrderReqVo;
import com.zhenxuan.tradeapi.common.vo.PayOrderRespVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付服务实现类
 */
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private WXPayUnifiedOrder wxPayUnifiedOrder;

    @Autowired

    @Override
    public PayOrderRespVo payOrder(PayOrderReqVo reqVo) {
        return null;
    }
}
