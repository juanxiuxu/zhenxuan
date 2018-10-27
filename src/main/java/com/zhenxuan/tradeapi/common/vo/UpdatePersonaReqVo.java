package com.zhenxuan.tradeapi.common.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UpdatePersonaReqVo extends BaseAuthReqVo implements Input {

    private String avatar;

    private String name; //nickName

    @Max(1)
    @Min(0)
    private int gender;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
