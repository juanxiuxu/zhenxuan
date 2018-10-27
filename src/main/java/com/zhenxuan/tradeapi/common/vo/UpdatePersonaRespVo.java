package com.zhenxuan.tradeapi.common.vo;

import com.zhenxuan.tradeapi.dao.entity.UserAuthEntity;

public class UpdatePersonaRespVo {

    private GetPersonaRespVo.User user;

    public GetPersonaRespVo.User getUser() {
        return user;
    }

    public void setUser(GetPersonaRespVo.User user) {
        this.user = user;
    }

    public UpdatePersonaRespVo(UserAuthEntity authEntity) {
        this.user = new GetPersonaRespVo.User();
        this.user.setAuthUid(authEntity.getAuthUid());
        this.user.setAvatar(authEntity.getAvatar());
        this.user.setGender(authEntity.getGender());
        this.user.setNickName(authEntity.getNickName());
    }
}
