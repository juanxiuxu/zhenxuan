///*
// * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
// */
//package com.zhenxuan.tradeapi.schedule.job;
//
//import com.baidu.fbu.settlement.common.enums.SignConfig;
//import com.baidu.fbu.settlement.common.http.HttpJSONHelper;
//import com.baidu.fbu.settlement.common.sign.Sign;
//import com.baidu.fbu.settlement.dao.RefundOrderMapper;
//import com.baidu.fbu.settlement.domain.model.OrderPushStatus;
//import com.baidu.fbu.settlement.domain.model.RefundOrder;
//import com.baidu.fbu.settlement.domain.res.HundSunCallBackRes;
//import com.google.common.collect.Maps;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
///**
// * Created by IntelliJ IDEA.
// * 定时push 退款接口处理结果到恒生
// *
// * @author tianyu07.
// * @date 16/7/4 20:32.
// */
//@Service
//public class RefundJob extends PushJob<RefundOrder> {
//
//    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RefundJob.class);
//
//    @Resource
//    private Sign bfbSign;
//
//    @Resource
//    private RefundOrderMapper refundOrderMapper;
//
//    @Override
//    protected void init() {
//        logger.info("push refund order job init");
//        setDataList(refundOrderMapper.getCallBackOrders(callBackStartTime, callBackEndTime));
//    }
//
//    @Override
//    public boolean doJob(RefundOrder tableDatas) {
//        OrderPushStatus pushStatus = OrderPushStatus.push_fail;
//        boolean result = false;
//
//        try {
//            logger.info("push refund order refundNo={}", tableDatas.getRefundNo());
//            HundSunCallBackRes res =
//                    HttpJSONHelper.instance().get(tableDatas.getPushUrl(), buildCallBackParam(tableDatas),
//                            HundSunCallBackRes.class);
//            if (res.getErrno() == 0) {
//                pushStatus = OrderPushStatus.push_success;
//                result = true;
//            }
//        } catch (Exception e) {
//            logger.error("push to hund sun failed" + e);
//        }
//        tableDatas.setPushStatus(pushStatus);
//        logger.info("update push status refundNo={},pushStatus={}", tableDatas.getRefundNo(), pushStatus.code);
//        refundOrderMapper.updateOrderStatus(null, pushStatus, tableDatas.getRefundNo());
//
//        return result;
//    }
//
//    private Map<String, Object> buildCallBackParam(RefundOrder tableDatas) {
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("refund_no", tableDatas.getRefundNo());
//        params.put("order_no", tableDatas.getOrderNo());
//        params.put("pay_channel", tableDatas.getPayChannel());
//        params.put("pay_channel_order_no", tableDatas.getPayChannelOrderNo());
//        params.put("cashback_result", tableDatas.getOrderRefundStatus().code);
//        params.put("cashback_amount", tableDatas.getCashbackAmount());
//        params.put("input_charset", 2);
//        params.put("version", 1);
//        params.put("sign", bfbSign.buildSign(SignConfig.HUND_SUN_SIGN, params));
//        return params;
//    }
//
//    @Override
//    protected void destroy() {
//        logger.info("PushJob done");
//    }
//
//}
