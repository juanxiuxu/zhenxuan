package com.zhenxuan.tradeapi.controller;

import com.zhenxuan.tradeapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {

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
    public Object payOrder() {

        return  null;
    }

}
