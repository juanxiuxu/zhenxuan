package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.ProductEntity;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {

    ProductEntity selectEntity(@Param("skuId") String skuId);

    int decreaseStockCount(@Param("skuId") String skuId, @Param("count") int count);

    int increaseStockCount(@Param("skuId") String skuId, @Param("count") int count);

    int increaseSalesCount(@Param("skuId") String skuId, @Param("count") int count);
}