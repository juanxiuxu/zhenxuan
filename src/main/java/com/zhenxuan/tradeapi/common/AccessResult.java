/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.common;

/**
 * 外联系统权限验证返回结果.
 * @author liuyiyang
 */
public class AccessResult {
    /** 返回码. **/
    private int status;
    /** 错误描述. **/
    private String msg;
    /** 时间戳. **/
    private String timestamp;
    /** 签名. **/
    private String sign;

    private String userName;

    /**
     *
     */
    public AccessResult() {
        super();
    }

    /**
     * @param status
     * @param msg
     * @param timestamp
     */
    public AccessResult(int status, String msg, String timestamp) {
        super();
        this.status = status;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    /**
     * @param status
     * @param msg
     * @param timestamp
     * @param sign
     */
    public AccessResult(int status, String msg, String timestamp, String sign) {
        super();
        this.status = status;
        this.msg = msg;
        this.timestamp = timestamp;
        this.sign = sign;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
