package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.PayTradeEnity;
import org.springframework.stereotype.Component;

@Component
public interface PayTradeMapper {

    int insertOrderEntity(PayTradeEnity payTradeEnity);

    int insertPayNotifyEntity(PayTradeEnity payTradeEnity);
}
