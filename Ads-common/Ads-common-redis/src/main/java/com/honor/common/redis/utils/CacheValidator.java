package com.honor.common.redis.utils;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;
import com.honor.common.base.exception.PlugException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
public class CacheValidator {

    public static void addValidate(String key) {
        if (StringUtils.isEmpty(key)) {
            log.error("CacheValidator.addValidate redis Key不能为空");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
    }

    public static void addValidate(String key, int timeout) {
        addValidate(key);
        if (timeout < 0) {
            log.error("CacheValidator.addValidate 超时时间不能为负数");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
    }

    public static void mapEntryValidate(Map.Entry<?, ?> entry) {
        if (entry == null) {
            log.error("CacheValidator.addValidate entry不能为空");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
        if (entry.getKey() == null) {
            log.error("CacheValidator.addValidate   entry key不能为空");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
        if (!(entry.getKey() instanceof String)) {
            log.error("CacheValidator.addValidate entry key必须是String类型");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
    }

}