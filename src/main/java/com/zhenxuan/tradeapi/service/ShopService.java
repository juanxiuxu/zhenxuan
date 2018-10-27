package com.zhenxuan.tradeapi.service;

import com.zhenxuan.tradeapi.dao.entity.ShopItem;

/**
 * 店铺service
 */
public interface ShopService {

    ShopItem getShop(String uid);
}
