package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderMapper {

    List<OrderEntity> selectEntityByUid(@Param("authUid") String authUid);

    int insertOrder(OrderEntity orderEntity);

    int updateOrderState(@Param("oid") String oid,
                         @Param("oldOrderStatus") Integer oldOrderStatus,
                         @Param("newOrderStatus") Integer newOrderStatus,
                        @Param("paidAt") Long paidAt);

    OrderEntity selectEntityByOid(@Param("oid") String orderId);

    // List<OrderEntity> selectEntityByUidAndCTime();

    List<OrderEntity> selectEntitiesByPaidAtRangeAndStatus(@Param("orderStatus") Integer orderStatus,
                                                            @Param("startPaidAt") Long startPaidAt,
                                                            @Param("endPaidAt") Long endPaidAt);

}
