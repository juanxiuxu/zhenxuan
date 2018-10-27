package com.zhenxuan.tradeapi.dao.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

/**
 * 店铺
 */
@DynamoDBTable(tableName = "zx_shop")
public class ShopItem extends Item {

    @DynamoDBHashKey(attributeName = "uid")
    private String uid;

    @DynamoDBAttribute(attributeName = "s_name")
    private String name;

    @DynamoDBAttribute(attributeName = "s_background")
    private String background;

    @DynamoDBAttribute(attributeName = "s_contact")
    private String contact;

    @DynamoDBAttribute(attributeName = "s_group_qr")
    private String groupQr;

    @DynamoDBAttribute(attributeName = "s_notice")
    private String notice;

    @DynamoDBAttribute(attributeName = "s_list")
    private List<String> gidList;

    // TODO:dynamodb
//    @DynamoDBVersionAttribute
//    private Long version;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

//    public Long getVersion() {
//        return version;
//    }
//
//    public void setVersion(Long version) {
//        this.version = version;
//    }
}
