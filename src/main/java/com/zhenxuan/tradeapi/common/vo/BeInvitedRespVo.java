package com.zhenxuan.tradeapi.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhenxuan.tradeapi.dao.entity.ShopItem;

import java.util.List;

public class BeInvitedRespVo {

    private String iuid;

    // 以下是tid的店铺
    @JsonProperty("s_name")
    private String name;

    @JsonProperty("s_background")
    private String background;

    @JsonProperty("s_contact")
    private String contact;

    @JsonProperty("s_group_qr")
    private String groupQr;

    @JsonProperty("s_notice")
    private String notice;

    @JsonProperty("s_list")
    private List<String> gidList;

    public BeInvitedRespVo copy(ShopItem shopItem) {
        if (shopItem != null) {
            this.setName(shopItem.getName());
            this.setContact(shopItem.getContact());
            this.setNotice(shopItem.getNotice());
            this.setBackground(shopItem.getBackground());
            this.setGroupQr(shopItem.getGroupQr());
            this.setGidList(shopItem.getGidList());
        }
        return this;
    }

    public String getIuid() {
        return iuid;
    }

    public void setIuid(String iuid) {
        this.iuid = iuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGroupQr() {
        return groupQr;
    }

    public void setGroupQr(String groupQr) {
        this.groupQr = groupQr;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<String> getGidList() {
        return gidList;
    }

    public void setGidList(List<String> gidList) {
        this.gidList = gidList;
    }
}
