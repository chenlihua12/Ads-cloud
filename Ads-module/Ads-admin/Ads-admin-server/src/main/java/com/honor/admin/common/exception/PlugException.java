package com.honor.admin.common.exception;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;

/**
 * 插件异常类，尽量在业务中不要用
 */
public class PlugException extends RuntimeException {
    private Integer code;
    private String i18n;
    private String msg;

    public PlugException(Integer code, String i18n, String message) {
        super(message);
        this.msg = message;
        this.i18n = i18n;
        this.code = code;
    }

    public static PlugException I18N_SUCCESS(String i18n) {
        return new PlugException(ErrorConstant.SUCCESS, i18n, "OK");
    }

    public static PlugException MSG_SUCCESS(String message) {
        return new PlugException(ErrorConstant.SUCCESS, I18nConstant.SUCCESS, message);
    }

    public static PlugException SUCCESS() {
        return new PlugException(ErrorConstant.SUCCESS, I18nConstant.SUCCESS, "OK");
    }

    public static PlugException SUCCESS(String i18n, String message) {
        return new PlugException(ErrorConstant.SUCCESS, i18n, message);
    }

    public static PlugException I18N_FAILURE(String i18n) {
        return new PlugException(ErrorConstant.FAILURE, i18n, "FAILURE");
    }

    public static PlugException MSG_FAILURE(String message) {
        return new PlugException(ErrorConstant.FAILURE, I18nConstant.FAILURE, message);
    }

    public static PlugException FAILURE() {
        return new PlugException(ErrorConstant.FAILURE, I18nConstant.FAILURE, "FAILURE");
    }

    public static PlugException FAILURE(String i18n, String message) {
        return new PlugException(ErrorConstant.FAILURE, i18n, message);
    }

    public static PlugException INSTANCE(Integer code, String i18n, String message) {
        return new PlugException(code, i18n, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
