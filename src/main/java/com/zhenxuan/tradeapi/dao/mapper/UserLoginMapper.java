package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.UserLoginEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserLoginMapper {

    UserLoginEntity selectEntityByUniqueKey(@Param("wxAppType") String wxAppType, @Param("wxOpenId") String wxOpenId);

    UserLoginEntity selectEntityByUid(@Param("loginUid") String loginUid);

    int insertLoginInfo(UserLoginEntity loginEntity);

    int updateLoginInfoByUniqueKey(UserLoginEntity loginEntity);

    int updateUnionIdByUid(@Param("loginUid") String loginUid, @Param("unionId") String unionId);
}
