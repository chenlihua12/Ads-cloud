package com.honor.common.base.enums;

/**
 * 数据状态
 */
public enum ValidEnum {

    ENABLE("E"), // 可用
    DISABLE("D"),// 禁用
    CANCEL("C");//撤销

    private String code;

    ValidEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
