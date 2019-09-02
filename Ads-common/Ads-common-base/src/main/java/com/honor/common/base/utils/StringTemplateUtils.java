package com.honor.common.base.utils;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串模板替换
 */
public class StringTemplateUtils {

    public static final String DEF_REGEX = "\\$\\{(.+?)\\}";

    /**
     * 对象模板替换 默认替换${}
     *
     * @param template
     * @param obj
     * @return
     */
    public static <T> String render(String template, T obj) throws Exception {
        Map data = JavaBeanUtils.objectToMap(obj);
        return render(template, data, DEF_REGEX);
    }

    /**
     * 默认替换${}
     *
     * @param template
     * @param data
     * @return
     */
    public static String render(String template, Map<String, String> data) {
        return render(template, data, DEF_REGEX);
    }

    /**
     * 对象模板替换 自定义替换模板
     *
     * @param template
     * @param obj
     * @return
     */
    public static <T> String render(String template, T obj, String regex) throws Exception {
        Map data = JavaBeanUtils.objectToMap(obj);
        return render(template, data, regex);
    }

    /**
     * 自定义替换模板
     *
     * @param template
     * @param data
     * @return
     */
    public static String render(String template, Map<String, String> data, String regex) {
        if (StringUtils.isEmpty(template)) {
            return "";
        }
        if (StringUtils.isEmpty(regex)) {
            return template;
        }
        if (data == null || data.size() == 0) {
            return template;
        }
        try {
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                String name = matcher.group(1);
                String value = data.get(name);
                if (value == null) {
                    value = "";
                }
                matcher.appendReplacement(sb, value);
            }
            matcher.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }

    public static String limit(String str, Integer count) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() > count) {
            return str.substring(0, count);
        }
        return str;
    }
}
