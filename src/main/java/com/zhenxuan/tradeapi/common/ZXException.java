package com.zhenxuan.tradeapi.common;

import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;

/**
 * 自定义异常，用于从内部传递错误信息并统一处理后返回给客户端
 */
public class ZXException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResultStatusCode resultStatusCode;

    private String extraMsg;

//    public ZXException() {
//        super();
//    }

//    public ZXException(Throwable e) {
//        super(e);
//    }
//
//    public ZXException(String msg) {
//        super(msg);
//    }
    
//    public ZXException(int errCode, String msg) {
//        super(msg);
//        setErrorcode(errCode);
//    }

    public ZXException(ResultStatusCode resultStatusCode) {
        super(resultStatusCode.desc);
        this.resultStatusCode = resultStatusCode;
    }

    public ZXException(ResultStatusCode resultStatusCode, String extraMsg) {
        super(String.format("%s %s", resultStatusCode.desc, extraMsg));
        this.resultStatusCode = resultStatusCode;
        this.extraMsg = extraMsg;
    }

    public ResultStatusCode getResultStatusCode() {
        return resultStatusCode;
    }

    public String getExtraErrMsg() {
        return extraMsg;
    }
}
