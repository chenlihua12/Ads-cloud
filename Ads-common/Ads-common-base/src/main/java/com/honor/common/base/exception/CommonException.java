package com.honor.common.base.exception;

import com.honor.common.base.constant.ErrorConstant;

/**
 * 公共异常处理类
 */
public class CommonException extends RuntimeException {
    private Integer code;
    private String msg;

    public CommonException(Integer code, String message) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public static CommonException SUCCESS() {
        return new CommonException(ErrorConstant.SUCCESS, "OK");
    }

    public static CommonException SUCCESS(String message) {
        return new CommonException(ErrorConstant.SUCCESS, message);
    }

    public static CommonException FAILURE() {
        return new CommonException(ErrorConstant.FAILURE, "FAILURE");
    }

    public static CommonException FAILURE(String message) {
        return new CommonException(ErrorConstant.FAILURE, message);
    }

    public static CommonException ERROR(String message) {
        return new CommonException(ErrorConstant.EXCEPTION_FAILURE, message);
    }


    public static CommonException INSTANCE(Integer code, String message) {
        return new CommonException(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
