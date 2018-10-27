package com.zhenxuan.tradeapi.service.impl;

import com.zhenxuan.tradeapi.dao.mapper.PayTradeMapper;
import com.zhenxuan.tradeapi.service.PaymentService;
import com.zhenxuan.tradeapi.thirdparty.WXPayUnifiedOrder;
import com.zhenxuan.tradeapi.common.vo.PayOrderReqVo;
import com.zhenxuan.tradeapi.common.vo.PayOrderRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付服务实现类
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private WXPayUnifiedOrder wxPayUnifiedOrder;

    @Autowired
    private PayTradeMapper payTradeMapper;

    @Override
    public PayOrderRespVo payOrder(PayOrderReqVo reqVo) {

       // wxPayUnifiedOrder.execute();
        return null;
    }
}
