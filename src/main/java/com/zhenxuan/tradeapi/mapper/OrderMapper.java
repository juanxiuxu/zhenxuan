package com.zhenxuan.tradeapi.mapper;

import com.zhenxuan.tradeapi.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderMapper {

    int insertOrder(OrderEntity orderEntity);
}
