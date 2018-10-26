/* Copyright © 2010 www.myctu.cn. All rights reserved. */
/**
 * project : ctu-utils
 * user created : pippo
 * date created : 2013-5-8 - 下午5:14:16
 */
package com.zhenxuan.tradeapi.common.http;

/**
 * UnexpectedHttpStatusException
 *
 * @author pippo
 * @since 2013-5-8
 */
public class HttpRouteException extends RuntimeException {

    private static final long serialVersionUID = -4664410630156843862L;

    public HttpRouteException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public HttpRouteException(String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public HttpRouteException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public HttpRouteException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public HttpRouteException(Throwable cause) {
        super(cause);
    }

    public HttpRouteException(int status) {
        super();
        this.status = status;
    }

    public HttpRouteException(int status, String message) {
        super(message);
        this.status = status;
    }

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
