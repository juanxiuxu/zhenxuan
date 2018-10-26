package com.zhenxuan.tradeapi.common.enums;

public enum ResultStatusCode {

    // 分区段 00
    SUCCESS(0, "ok"), //
    INTERNAL_ERROR(1, "internal error"), //
    PARAM_ERROR(2, "param error"), //
    SIGN_ERROR(3, "sign error"), //
    //    NO_SUCH_PAYCHANNEL(4, "no such paychannel"), //
//    FAIL_TO_ACCESS_PAYCHANNEL(5, "fail to access pay channel"), //
    DB_ERROR(6, "db error"), //
    //    DUPLICATE_INSERT_ORDER(7, "业务订单提交重复"),
    HTTP_ERROR(8, "remote request fail"),
    INVALID_TOKEN(9, "invalid token"),
    NO_TOKEN(10, "no token"),

    NO_LOGIN_UID(11, "no login uid"), // TODO: modify code to 50xx
    NO_AUTH_UID(12, "no auth uid"),
    USER_NOT_EXISTS(13, "user not exists"),
    UNMATCHED_LOGIN_UID(13, "login uid unmatched for token"),
    UNMATCHED_AUTH_UID(14, "auth uid unmatched for token"),
    DECRYPT_USERINFO_FAILED(15, "decrypt user info is failed"),
    UNMATCHED_APPID(16, "unmatched appid"),

    OTHER_UNKNOWN_ERROR(99, "其他异常"),

    // 登陆错误 5000开头
    NO_CODE_ERROR(5001, "need code"),
    INVALID_CODE_ERROR(5002, "invalid code"),
    LOGIN_ERROR(5003, "login error"),   // 前端特殊处理了，不能改变
    UPDATE_SESSION_KEY_ERROR(5004, "更新session-key错误"),
    USER_NOT_LOGGED_IN(5012, "用户未登陆，不存在改用户"),
//    QUERY_ORDERINFO_FAILED(100, "query pay orderinfo fail"),
//    NOTIFY_PAGECALLBACK_FAILED(101, "notity pageCallback fail")
    DONOT_INVITED_SELF(5013, "不能邀请自己"),


    // 订单错误 6000开头
    INVALID_SKUID_FORMAT(6000, "invalid skuId format"),
    SPUITEM_NOT_FOUND(6001, "spu item not found"),
    SKUITEMS_NOT_FOUND(6002, "sku items not found"),
    GOODS_DATA_ERROR(6003, "goods data error"),
    NO_GOODS_FOR_SKUID(6004, "no goods for this skuId"),
    GOODS_STOCK_NOT_ENOUGH(6005, "goods stock not enough"),
    GOODS_NOT_SHOW(6006, "goods not show");

    public final int code;
    public final String desc;

    ResultStatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
