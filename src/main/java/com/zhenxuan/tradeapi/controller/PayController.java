package com.zhenxuan.tradeapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayController {

    @RequestMapping(value = "/pay/wx/order", method = RequestMethod.POST)
    @ResponseBody
    public Object payOrder() {
        return  null;
    }

}
