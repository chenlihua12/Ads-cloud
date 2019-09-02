package com.honor.common.base.enums;

public enum DateFormatEnum {

    DEFAULT_PATTERN("yyyy-MM-dd HH:mm:ss"),
    yyyy_MM_ddEHHcmm("yyyy-MM-dd HH:mm"),
    yyyy_MM_ddEHH("yyyy-MM-dd HH"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyyy_MM("yyyy-MM"),

    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    yyyyMMddHHmm("yyyyMMddHHmm"),
    yyyyMMddHH("yyyyMMddHH"),
    yyyyMMdd("yyyyMMdd"),
    yyyyMM("yyyyMM"),
    yyyy("yyyy"),

    HHcmmcss("HH:mm:ss"),
    HHcmm("HH:mm"),
    HH("HH"),
    mmcss("mm:ss");

    private String code;

    DateFormatEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
