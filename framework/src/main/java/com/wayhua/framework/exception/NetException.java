package com.wayhua.framework.exception;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/1/26.
 */
public class NetException extends RuntimeException {
    private final String errorMsg;

    public NetException(String reason) {
        this.errorMsg = reason;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}