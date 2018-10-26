package com.zhenxuan.tradeapi.mapper;

import com.amazonaws.services.glue.model.Order;
import com.zhenxuan.tradeapi.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface OrderMapper {

    OrderEntity selectEntityByUid(@Param("authUid") String authUid);

    int insertOrder(OrderEntity orderEntity);

    int updateOrderState(@Param("oid") String oid, @Param("orderStatus") int orderStatus, @Param("paidAt") Date paidAt);

}
