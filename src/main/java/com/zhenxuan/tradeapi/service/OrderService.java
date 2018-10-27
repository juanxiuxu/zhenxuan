package com.zhenxuan.tradeapi.service;

import com.zhenxuan.tradeapi.common.vo.CreateOrderDirectReqVo;
import com.zhenxuan.tradeapi.common.vo.CreateOrderDirectRespVo;

/**
 * orderservice
 */
public interface OrderService {

    CreateOrderDirectRespVo createOrderDirect(CreateOrderDirectReqVo createOrderDirectReqVo);
}
