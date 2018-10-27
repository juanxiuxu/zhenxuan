package com.zhenxuan.tradeapi.controller;

import com.zhenxuan.tradeapi.common.ResultBody;
import com.zhenxuan.tradeapi.service.OrderService;
import com.zhenxuan.tradeapi.common.vo.CreateOrderDirectReqVo;
import com.zhenxuan.tradeapi.common.vo.CreateOrderDirectRespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order/create-sku-direct", method = RequestMethod.POST)
    @ResponseBody
    public Object createOrderDirect(@RequestBody CreateOrderDirectReqVo createOrderDirectReqVo) {
        CreateOrderDirectRespVo respVo = orderService.createOrderDirect(createOrderDirectReqVo);
        return ResultBody.success(respVo);
    }
}
