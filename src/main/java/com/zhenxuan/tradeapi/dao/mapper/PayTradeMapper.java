package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.PayTradeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface PayTradeMapper {

    int insertOrderEntity(PayTradeEntity payTradeEntity);

    PayTradeEntity selectEntityByOid(@Param("orderId") String orderId);

    // List<PayTradeEntity> selectEntitiesByCond(PayTradeEntity payTradeEntity);

    int updatePayNotifyResult(PayTradeEntity payTradeEntity);
}
