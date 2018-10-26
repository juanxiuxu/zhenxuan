package com.zhenxuan.tradeapi.service;

import com.zhenxuan.tradeapi.vo.CreateOrderDirectReqVo;
import com.zhenxuan.tradeapi.vo.CreateOrderDirectRespVo;

/**
 * orderservice
 */
public interface OrderService {

    CreateOrderDirectRespVo createOrderDirect(CreateOrderDirectReqVo createOrderDirectReqVo);
}
