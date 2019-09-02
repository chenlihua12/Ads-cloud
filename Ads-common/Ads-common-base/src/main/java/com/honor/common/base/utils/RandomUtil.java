package com.honor.common.base.utils;


import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 权重随机
 * <p>
 *
 * @author yongheng
 * @since 2018/12/20
 */
public class RandomUtil {

    /**
     * 权重随机<br>
     *
     * @return:
     * @Author: yongheng
     * @Date: 2018/12/24 16:31
     */
    public static String weightRandom(Map<String, Integer> map) {
        Set<String> keySet = map.keySet();
        List<String> weights = new ArrayList<String>();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String weightStr = it.next();
            int weight = map.get(weightStr);
            for (int i = 0; i <= weight; i++) {
                weights.add(weightStr);
            }
        }
        int idx = RandomUtils.nextInt(0, weights.size());
        return weights.get(idx);
    }

    /**
     * 范围随机<br>
     *
     * @return:
     * @Author: yongheng
     * @Date: 2018/12/24 16:31
     */
    public static double randomRange(double low, double high) {
        return (Math.random() * (high - low)) + low;
    }

    /**
     * 随机生成0.01 - 0.99 的随机金额
     * @Author: luosiwen
     * @Date: 2019/05/06 10:16
     * @return
     */
    public static BigDecimal randomBigDecimal() {
        double number = Math.random();
        BigDecimal amount = new BigDecimal(number);
        BigDecimal retrueBigDecimal = amount.setScale(2, BigDecimal.ROUND_UP);
        if(retrueBigDecimal.compareTo(BigDecimal.ZERO)<= 0 || retrueBigDecimal.compareTo(BigDecimal.ONE)>=0){
            return randomBigDecimal();
        }
        return retrueBigDecimal;
    }
}