package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.UserBalanceBillEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserBalanceBillMapper {

    int insertEntity(UserBalanceBillEntity entity);
}
