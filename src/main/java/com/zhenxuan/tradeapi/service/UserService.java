package com.zhenxuan.tradeapi.service;

import com.zhenxuan.tradeapi.dao.entity.UserAuthEntity;
import com.zhenxuan.tradeapi.dao.entity.UserLoginEntity;
import com.zhenxuan.tradeapi.common.vo.*;

public interface UserService {

    LoginRespVo login(LoginReqVo loginReqVo);

    String auth(AuthReqVo authReqVo);

    UserLoginEntity verifyLoginUid(String loginUid);

    UserAuthEntity verifyAuthUid(String authUid);

    UpdatePersonaRespVo updatePersona(UpdatePersonaReqVo updatePersonaReqVo);

    GetPersonaRespVo getPersona(GetPersonaReqVo getPersonaReqVo);

    GetUserActionRespVo getAction(GetUserActionReqVo actionReqVo);

    EnterShopRespVo enterShop(EnterShopReqVo enterShopReqVo);

    BeInvitedRespVo beInvited(BeInvitedReqVo beInvitedReqVo);
}
