package com.zhenxuan.tradeapi.service.impl;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.common.vo.PayOrderReqVo;
import com.zhenxuan.tradeapi.common.vo.PayOrderRespVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayBaseVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayDirectNotifyRespVo;
import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import com.zhenxuan.tradeapi.dao.entity.PayTradeEntity;
import com.zhenxuan.tradeapi.dao.entity.UserAuthEntity;
import com.zhenxuan.tradeapi.dao.entity.UserBalanceBillEntity;
import com.zhenxuan.tradeapi.dao.mapper.OrderMapper;
import com.zhenxuan.tradeapi.dao.mapper.PayTradeMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserAuthMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserBalanceBillMapper;
import com.zhenxuan.tradeapi.domain.WXPayResultInfo;
import com.zhenxuan.tradeapi.domain.WXUnifiedOrderInfo;
import com.zhenxuan.tradeapi.service.PaymentService;
import com.zhenxuan.tradeapi.thirdparty.WXPayUnifiedOrder;
import com.zhenxuan.tradeapi.thirdparty.WXPayUnifiedOrderNotify;
import com.zhenxuan.tradeapi.utils.GlobalIdUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付服务实现类
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private WXPayUnifiedOrder wxPayUnifiedOrder;

    @Autowired
    private WXPayUnifiedOrderNotify wxPayUnifiedOrderNotify;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PayTradeMapper payTradeMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserBalanceBillMapper userBalanceBillMapper;

    /**
     * 支付下单
     * @param reqVo
     * @return
     */
    @Override
    public PayOrderRespVo payOrder(PayOrderReqVo reqVo) {
        OrderEntity orderEntity = orderMapper.selectEntityByOid(reqVo.getOrderId());
        if (orderEntity == null) {
            logger.error("order not exists. orderId:{}", reqVo.getOrderId());
            throw new ZXException(ResultStatusCode.ORDER_ID_NOT_EXISTS);
        }

        String uid = orderEntity.getAuthUid();
        UserAuthEntity authEntity = userAuthMapper.selectEntityByUid(uid);
        if (authEntity == null) {
            logger.error("user not exists. authUid:{}", uid);
            throw new ZXException(ResultStatusCode.USER_NOT_EXISTS);
        }

        long balancePayFee = calcBalancePayFee(orderEntity,authEntity, reqVo);
        long wxPayFee = orderEntity.getTotal() - balancePayFee;
        WXUnifiedOrderInfo wxOrderInfo = wxPayUnifiedOrder.execute(orderEntity, authEntity.getWxOpenId(), wxPayFee);

        if (reqVo.isUseBalance() && balancePayFee != 0) {
            payWithBalance(orderEntity, wxOrderInfo, authEntity, balancePayFee);
        } else {
            payWithWX(orderEntity, wxOrderInfo);
        }

        PayOrderRespVo respVo = new PayOrderRespVo();
        respVo.setAllBalance(wxPayFee == 0);
        respVo.setPayload(new PayOrderRespVo.Payload(wxOrderInfo.getAppId(), wxOrderInfo.getPrepayId()));

        return respVo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void payWithBalance(OrderEntity orderEntity, WXUnifiedOrderInfo wxOrderInfo,
                                 UserAuthEntity authEntity, long balancePayFee) {
        PayTradeEntity tradeEntity = PayTradeEntity.create(orderEntity, wxOrderInfo);
        payTradeMapper.insertOrderEntity(tradeEntity);

        UserBalanceBillEntity billEntity = new UserBalanceBillEntity();
        billEntity.setBillId(GlobalIdUtil.newOrderId());
        billEntity.setAuthUid(authEntity.getAuthUid());
        billEntity.setIncome(1);
        billEntity.setAmount(balancePayFee);
        billEntity.setDesc("余额消费");
        billEntity.setOrderId(orderEntity.getOid());
        billEntity.setInitBalance(authEntity.getBalance());
        billEntity.setFinalBalance(authEntity.getBalance() - balancePayFee);

        userBalanceBillMapper.insertEntity(billEntity);
        int result = userAuthMapper.decreaseUserBalance(authEntity.getAuthUid(), balancePayFee);
        if (result == 0) {
            throw new ZXException(ResultStatusCode.USER_BALANCE_NOT_ENOUGH);
        }
    }

    private void payWithWX(OrderEntity orderEntity, WXUnifiedOrderInfo wxOrderInfo) {
        PayTradeEntity tradeEntity = PayTradeEntity.create(orderEntity, wxOrderInfo);
        payTradeMapper.insertOrderEntity(tradeEntity);
    }

    private long calcBalancePayFee(OrderEntity orderEntity, UserAuthEntity authEntity, PayOrderReqVo reqVo) {
        long balancePayFee;
        long orderTotalFee = orderEntity.getTotal();
        if (reqVo.isUseBalance()) {
            if (authEntity.getBalance() <= orderTotalFee) {
                balancePayFee = authEntity.getBalance();
            } else {
                balancePayFee = orderTotalFee;
            }
        } else {
            balancePayFee = 0;
        }
        return balancePayFee;
    }

    /**
     * 支付结果回调
     * @param
     * @return
     */
    @Override
    public WXPayDirectNotifyRespVo payOrderNotify(String notifyXml) {
        WXPayDirectNotifyRespVo respVo = new WXPayDirectNotifyRespVo();
        if (StringUtils.isEmpty(notifyXml)) {
            respVo.setReturnCode(WXPayBaseVo.WXPayRespCode.FAIL.code);
            return respVo;
        }

        WXPayResultInfo wxResult = wxPayUnifiedOrderNotify.execute(notifyXml);

        String orderId = wxResult.getOrderId();
        PayTradeEntity oldTradeEntity = payTradeMapper.selectEntityByOid(orderId);
        if (oldTradeEntity == null) {
            logger.error("pay trade not exists by orderId:[{}]", orderId);
            respVo.setReturnCode(WXPayBaseVo.WXPayRespCode.FAIL.code);
            return respVo;
        }

        PayTradeEntity newTradeEntity = PayTradeEntity.create(wxResult);
        payTradeMapper.updatePayNotifyResult(newTradeEntity);

        respVo.setReturnCode(WXPayBaseVo.WXPayRespCode.SUCCESS.code);
        return respVo;
    }
}
