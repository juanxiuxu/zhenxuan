package com.zhenxuan.tradeapi.service.impl;

import com.zhenxuan.tradeapi.dao.entity.ShopItem;
import com.zhenxuan.tradeapi.service.ShopService;
import com.zhenxuan.tradeapi.thirdparty.DynamoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 店铺服务
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private DynamoDbService dynamoDbService;

    @Override
    public ShopItem getShop(String uid) {
        return dynamoDbService.read(ShopItem.class, uid, true);
    }
}
