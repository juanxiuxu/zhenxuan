package com.zhenxuan.tradeapi.controller;

import com.zhenxuan.tradeapi.common.ResultBody;
import com.zhenxuan.tradeapi.common.constants.Constants;
import com.zhenxuan.tradeapi.common.vo.PayOrderReqVo;
import com.zhenxuan.tradeapi.common.vo.PayOrderRespVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayDirectNotifyReqVo;
import com.zhenxuan.tradeapi.service.PaymentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

//    /**
//     * 绑卡
//     * @return
//     */
//    @RequestMapping(value = "/passport/user/bank-binded", method = RequestMethod.POST)
//    @ResponseBody

    /**
     * 支付订单
     */
    @RequestMapping(value = "/pay/wx/order", method = RequestMethod.POST)
    @ResponseBody
    public Object payOrder(@RequestBody PayOrderReqVo reqVo, @RequestParam(value = Constants.AUTH_UID) String uid) {
        reqVo.setAuthUid(uid);
        PayOrderRespVo respVo = paymentService.payOrder(reqVo);
        return ResultBody.success(respVo);
    }

    @RequestMapping(value = "/pay/unifiedorder/notify", method = RequestMethod.POST)
    @ResponseBody
    public Object payOrderNotify(@RequestBody String notifyXml) {
        paymentService.payOrderNotify(notifyXml);
    }
}
