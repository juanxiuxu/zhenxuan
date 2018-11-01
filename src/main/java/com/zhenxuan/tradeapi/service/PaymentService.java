package com.zhenxuan.tradeapi.service;

import com.zhenxuan.tradeapi.common.vo.PayOrderReqVo;
import com.zhenxuan.tradeapi.common.vo.PayOrderRespVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayDirectNotifyRespVo;

/**
 * 支付服务接口，针对所有与资金相关的行为，包括支付，退款，绑定银行卡，充值，提现等
 */
public interface PaymentService {

    PayOrderRespVo payOrder(PayOrderReqVo reqVo);

    WXPayDirectNotifyRespVo payOrderNotify(String notifyXml);

    // TODO: refund, bindcard, drawback
}
