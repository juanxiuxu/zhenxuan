package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.PayTradeEntity;
import org.springframework.stereotype.Component;

@Component
public interface PayTradeMapper {

    int insertOrderEntity(PayTradeEntity payTradeEntity);

    int insertPayNotifyEntity(PayTradeEntity payTradeEntity);
}
