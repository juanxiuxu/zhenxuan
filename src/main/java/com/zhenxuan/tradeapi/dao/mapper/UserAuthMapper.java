package com.zhenxuan.tradeapi.dao.mapper;

import com.zhenxuan.tradeapi.dao.entity.UserAuthEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserAuthMapper {

    UserAuthEntity selectEntityByUnionId(@Param("unionId") String unionId);

    UserAuthEntity selectEntityByUid(@Param("authUid") String authUid);

    int insertPersona(UserAuthEntity authEntity);

    int updatePersonaByUnionId(@Param("unionId") String unionId, @Param("avatar") String avatar, @Param("nickName") String nickName, @Param("gender") Integer gender);

    int updatePersonaByUid(@Param("authUid") String authUid, @Param("avatar") String avatar, @Param("nickName") String nickName, @Param("gender") Integer gender);

    int updateActionByUid(@Param("authUid") String authUid, @Param("tid") String tid,
                          @Param("iuid") String iuid, @Param("fwhOpenId") String fwhOpenId,
                          @Param("member") int member, @Param("expired") int expired);
}
