package com.honor.common.base.utils;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.exception.CommonException;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 通用校验器
 */
public class ValidatorHelperUtils {

    /**
     * 检查是否为空,为空抛异常
     *
     * @param value
     * @param errMsg
     */
    public static void isEmptyThrow(String value, String errMsg) {
        isEmptyThrow(value, ErrorConstant.FAILURE, errMsg);
    }

    /**
     * 检查是否为空,为空抛异常
     *
     * @param value
     * @param code
     * @param errMsg
     */
    public static void isEmptyThrow(String value, Integer code, String errMsg) {
        if (StringUtils.isEmpty(value)) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查Bool,为true抛异常
     *
     * @param value
     * @param errMsg
     */
    public static void isTrueThrow(Boolean value, String errMsg) {
        isTrueThrow(value, ErrorConstant.FAILURE, errMsg);
    }

    /**
     * 检查Bool,为true抛异常
     *
     * @param value
     * @param code
     * @param errMsg
     */
    public static void isTrueThrow(Boolean value, Integer code, String errMsg) {
        if (value) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查Bool,为false抛异常
     *
     * @param value
     * @param errMsg
     */
    public static void isFalseThrow(Boolean value, String errMsg) {
        isFalseThrow(value, ErrorConstant.FAILURE, errMsg);
    }

    /**
     * 检查Bool,为false抛异常
     *
     * @param value
     * @param code
     * @param errMsg
     */
    public static void isFalseThrow(Boolean value, Integer code, String errMsg) {
        if (!value) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查对象是否空，为空抛异常
     *
     * @param obj
     * @param errMsg
     * @throws
     */
    public static void isNullThrow(Object obj, String errMsg) {
        if (obj == null) {
            throw CommonException.INSTANCE(ErrorConstant.FAILURE, errMsg);
        }
    }

    /**
     * 检查对象是否空，为空抛异常
     *
     * @param obj
     * @param code
     * @param errMsg
     */
    public static void isNullThrow(Object obj, Integer code, String errMsg) {
        if (obj == null) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查对象是否空，不为空抛异常
     *
     * @param obj
     * @param errMsg
     * @throws
     */
    public static void noNullThrow(Object obj, String errMsg) {
        if (obj != null) {
            throw CommonException.INSTANCE(ErrorConstant.FAILURE, errMsg);
        }
    }

    /**
     * 检查对象是否空，不为空抛异常
     *
     * @param obj
     * @param code
     * @param errMsg
     */
    public static void noNullThrow(Object obj, Integer code, String errMsg) {
        if (obj != null) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }


    /**
     * 检查对象数据是否为空
     *
     * @param obj
     * @param errMsg
     */
    public static void arrayIsEmptyThrow(Object[] obj, String errMsg) {
        if (obj == null || obj.length == 0) {
            throw CommonException.INSTANCE(ErrorConstant.FAILURE, errMsg);
        }
    }

    /**
     * 检查对象数据是否为空
     *
     * @param obj
     * @param code
     * @param errMsg
     */
    public static void arrayIsEmptyThrow(Object[] obj, Integer code, String errMsg) {
        if (obj == null || obj.length == 0) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查对象数据是否为空
     *
     * @param obj
     * @param errMsg
     */
    public static void listIsEmptyThrow(List obj, String errMsg) {
        if (obj == null || obj.size() == 0) {
            throw CommonException.INSTANCE(ErrorConstant.FAILURE, errMsg);
        }
    }

    /**
     * 检查对象数据是否为空
     *
     * @param obj
     * @param code
     * @param errMsg
     */
    public static void listIsEmptyThrow(List obj, Integer code, String errMsg) {
        if (obj == null || obj.size() == 0) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查两个参数是否相等,不相等抛异常
     */
    public static void noEqualsThrow(String value1, String value2, String errMsg) {
        noEqualsThrow(value1, value2, ErrorConstant.FAILURE, errMsg);
    }

    /**
     * 检查两个参数是否相等,不相等抛异常
     */
    public static void noEqualsThrow(String value1, String value2, Integer code, String errMsg) {
        if (!value1.equals(value2)) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }

    /**
     * 检查两个参数是否相等,不相等抛异常
     */
    public static void isEqualsThrow(String value1, String value2, String errMsg) {
        isEqualsThrow(value1, value2, ErrorConstant.FAILURE, errMsg);
    }

    /**
     * 检查两个参数是否相等,不相等抛异常
     */
    public static void isEqualsThrow(String value1, String value2, Integer code, String errMsg) {
        if (value1.equals(value2)) {
            throw CommonException.INSTANCE(code, errMsg);
        }
    }
}
