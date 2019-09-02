package com.honor.common.base.utils;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;
import com.honor.common.base.exception.PlugException;

import java.util.Random;

/**
 * 随机码生成工具类
 */
public class RandomGeneratorUtils {
    private static final char[] pool = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    /**
     * 默认生成6位随机码
     *
     * @return
     */
    public static String getCode() {
        Random random = new Random();
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(10);
            buff.append(pool[index]);
        }
        return buff.toString().toUpperCase();
    }

    /**
     * 根据长度生成随机码
     *
     * @param length
     * @return
     */
    public static String getCode(int length) {
        if (length < 1) {
            throw PlugException.INSTANCE(ErrorConstant.PARAMS_ERROR, I18nConstant.RANDOM_LENGTH_NEED_GT_0, "随机验证码长度必须大于零 ！");
        }
        Random random = new Random();
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            buff.append(pool[index]);
        }
        return buff.toString().toUpperCase();
    }

    /**
     *
     * 随机生成验证码（数字+字母）
     *
     * @param len 邀请码长度
     * @return
     *
     * @author yongheng
     * @date 2019年03月11日 上午9:27:09
     */
    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "23456789abcdefghgklmnpqrstuvwxyz";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }
}
