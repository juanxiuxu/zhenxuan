package com.zhenxuan.tradeapi.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.utils.GlobalIdUtil;

public class ResultBody {

    @JsonProperty("error_code")
    private int code;

    private Object data;

    @JsonProperty("error_msg")
    private String msg;

    @JsonProperty("req_id")
    private String requestId;

    public static ResultBody success(Object data) {
        return new ResultBody(ResultStatusCode.SUCCESS, data);
    }

    public static ResultBody failure(ResultStatusCode resultStatusCode) {
        return new ResultBody(resultStatusCode);
    }

    public static ResultBody failure(ResultStatusCode resultStatusCode, String extraMsg) {
        return new ResultBody(resultStatusCode.getCode(), String.format("%s %s", resultStatusCode.getDesc(), extraMsg));
    }

    private ResultBody() {
    }

    public ResultBody(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.requestId = GlobalIdUtil.newRequestId();
    }

    private ResultBody(ResultStatusCode resultStatusCode) {
        this.code = resultStatusCode.code;
        this.msg = resultStatusCode.desc;
        this.data = new JSONObject();
        this.requestId = GlobalIdUtil.newRequestId();
    }

    private ResultBody(ResultStatusCode resultStatusCode, Object data) {
        this(resultStatusCode);
        this.data = data;
        this.requestId = GlobalIdUtil.newRequestId();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
