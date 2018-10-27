package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhenxuan.tradeapi.dao.entity.UserLoginEntity;
import org.apache.commons.lang.StringUtils;

/**
 * 获取用户行为响应类
 */
public class GetUserActionRespVo {

    @JsonProperty("union_id")
    private int hasAuthed; // 是否有unionId. 0: no, 1: yes

    @JsonProperty("is_fwh_user")
    private int hasFocusedFwh; // 是否关注过公众号. 0: no, 1: yes

    // tid, iuid

    public GetUserActionRespVo() {}

    public GetUserActionRespVo(UserLoginEntity loginEntity) {
        this.hasAuthed = StringUtils.isEmpty(loginEntity.getUnionId()) ? 0 : 1;
        this.hasFocusedFwh = StringUtils.isEmpty(loginEntity.getFwhOpenId()) ? 0 : 1;
    }

    public int getHasAuthed() {
        return hasAuthed;
    }

    public void setHasAuthed(int hasAuthed) {
        this.hasAuthed = hasAuthed;
    }

    public int getHasFocusedFwh() {
        return hasFocusedFwh;
    }

    public void setHasFocusedFwh(int hasFocusedFwh) {
        this.hasFocusedFwh = hasFocusedFwh;
    }
}
