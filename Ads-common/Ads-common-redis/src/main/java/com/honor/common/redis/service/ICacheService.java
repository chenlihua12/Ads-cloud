package com.honor.common.redis.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface ICacheService {

    /**
     * 方法用途: 放入缓存<br>
     * 实现步骤: <br>
     *
     * @param key
     * @param value
     */
    void add(String key, Object value);

    /**
     * 方法用途: 放入缓存<br>
     * 实现步骤: <br>
     *
     * @param key
     * @param value
     * @param timeout 必须大于等于0,0为永久,单位秒
     */
    <T> void add(String key, T value, int timeout);

    /**
     * 方法用途: 如果缓存不存在则添加<br>
     * 实现步骤: <br>
     *
     * @param key
     * @param value
     */
    <T> boolean addIfAbsent(String key, T value);

    /**
     * 方法用途: 如果缓存不存在则添加<br>
     * 实现步骤: <br>
     *
     * @param key
     * @param value
     * @param timeout 必须大于等于0,0为永久,单位秒
     */
    <T> boolean addIfAbsent(String key, T value, int timeout);

    /**
     * 方法用途: 查询缓存中key对应的value<br>
     * 查询map.entry的时候，key需要以|分割key和field<br>
     * 实现步骤: <br>
     *
     * @param key
     * @return
     */
    <V> V get(String key);

    /**
     * get:(从map中取值). <br/>
     *
     * @param redisKey
     * @param mapKey
     * @return
     * @author wangjingao
     * @since JDK 1.7
     */
    <T> T get(String redisKey, String mapKey);

    /**
     * 方法用途: 查询缓存中是否存在<br>
     * 实现步骤: <br>
     *
     * @param key
     * @return
     */
    Boolean exist(String key);

    /**
     * 方法用途: 删除缓存中对应的值<br>
     * 实现步骤: <br>
     *
     * @param key
     */
    void delete(String key);


    /**
     * 方法用途: 插入计数器<br>
     * 实现步骤: <br>
     *
     * @param key
     * @param value
     */
    Long incr(String key, int value, long timeout);

    /**
     * 计数并在指定时间失效
     *
     * @param key
     * @param value
     * @param date
     * @return
     */
    Long incrAndExpireAt(String key, int value, final Date date);

    /**
     * redis锁实现
     *
     * @param key
     * @param value
     * @return
     */
    Boolean setnx(final String key, final String value);

    /**
     * 设置失效期
     *
     * @param key
     * @param seconds
     * @return
     */
    Boolean expire(final String key, final int seconds);

    /**
     * 删除指令
     *
     * @param key
     */
    void del(String key);

    /**
     * 在某一个时间点失效
     *
     * @param key
     * @param date
     * @return
     */
    Boolean expireAt(String key, final Date date);

    /**
     * 获取失效时间
     *
     * @param key
     * @param timeUnit
     * @return
     */
    Long getExpire(String key, final TimeUnit timeUnit);


}
