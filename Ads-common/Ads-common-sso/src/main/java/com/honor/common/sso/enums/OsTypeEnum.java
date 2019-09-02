package com.honor.common.sso.enums;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public enum OsTypeEnum {

    IOS, ANDROID, WP, PC;

    public static OsTypeEnum parse(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (OsTypeEnum osTypeEnum : OsTypeEnum.values()) {
            if (osTypeEnum.toString().equals(code)) {
                return osTypeEnum;
            }
        }
        return null;
    }

    public static boolean isMobile(String code) {
        OsTypeEnum osTypeEnum = parse(code);
        return OsTypeEnum.ANDROID == osTypeEnum || OsTypeEnum.IOS == osTypeEnum;
    }

    public static boolean isMobile(HttpServletRequest request) {
        String code = request.getHeader("osType");
        OsTypeEnum osTypeEnum = parse(code);
        return OsTypeEnum.ANDROID == osTypeEnum || OsTypeEnum.IOS == osTypeEnum;
    }
}
