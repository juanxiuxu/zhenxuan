package com.zhenxuan.tradeapi.mapper;

import com.zhenxuan.tradeapi.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderItemMapper {

    int insertOrderItem(OrderItemEntity orderItemEntity);
}
