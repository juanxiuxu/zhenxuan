package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderMapper {

    List<OrderEntity> selectEntityByUid(@Param("authUid") String authUid);

    int insertOrder(OrderEntity orderEntity);

    int updateOrderState(@Param("oid") String oid, @Param("orderStatus") int orderStatus);

    OrderEntity selectEntityByOid(@Param("oid") String orderId);
}
