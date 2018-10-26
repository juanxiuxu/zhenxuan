package com.zhenxuan.tradeapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhenxuan.tradeapi.entity.UserAuthEntity;

/**
 * 获取用户画像响应类
 */
public class GetPersonaRespVo {

    public GetPersonaRespVo() {
    }

    public GetPersonaRespVo(UserAuthEntity authEntity) {
        this.user = new GetPersonaRespVo.User();
        this.user.setAuthUid(authEntity.getAuthUid());
        this.user.setAvatar(authEntity.getAvatar());
        this.user.setGender(authEntity.getGender());
        this.user.setNickName(authEntity.getNickName());
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {

        @JsonProperty("uid")
        private String authUid;

        private String avatar;

        @JsonProperty("user_name")
        private String nickName;

        private int gender;

        public String getAuthUid() {
            return authUid;
        }

        public void setAuthUid(String authUid) {
            this.authUid = authUid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }
}
