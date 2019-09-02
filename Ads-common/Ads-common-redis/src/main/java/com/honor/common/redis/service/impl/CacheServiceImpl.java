package com.honor.common.redis.service.impl;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;
import com.honor.common.base.exception.PlugException;
import com.honor.common.redis.service.ICacheService;
import com.honor.common.redis.utils.CacheValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void add(String key, Object value) {
        CacheValidator.addValidate(key);
        add(key, value, 0);
    }

    /**
     * 方法用途: 往缓存放入值,类型包括Map.Entry,Map,Set,List,其他类型将自动序列化为二进制<br>
     * 实现步骤: <br>
     *
     * @param key
     * @param value
     * @param timeout
     */
    @Override
    public <T> void add(String key, T value, int timeout) {
        CacheValidator.addValidate(key, timeout);
        long addedNum = 0;
        redisTemplate.opsForValue().set(key, value);
        addedNum = 1;

        // 设置过期时间
        if (timeout != 0 && !redisTemplate.expire(key, timeout, TimeUnit.SECONDS)) {
            log.error("CacheServiceImpl.add 存入redis异常");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
        if (addedNum == 0) {
            log.error("CacheServiceImpl.add 存入redis异常");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
    }

    @Override
    public <T> boolean addIfAbsent(String key, T value) {
        CacheValidator.addValidate(key);
        return addIfAbsent(key, value, 0);
    }

    @Override
    public <T> boolean addIfAbsent(String key, T value, int timeout) {
        CacheValidator.addValidate(key, timeout);
        boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (result && timeout != 0) {
            return redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
        }
        return result;
    }

    @Override
    public <T> T get(String key) {

        CacheValidator.addValidate(key);

        switch (redisTemplate.type(key)) {
            case STRING:
                return (T) redisTemplate.opsForValue().get(key);
            case LIST:
                return (T) redisTemplate.opsForList().range(key, 0, -1);
            case SET:
                return (T) redisTemplate.opsForSet().members(key);
            case ZSET:
                return (T) redisTemplate.opsForZSet().range(key, 0, -1);
            case HASH:
                if (key.split("\\|").length != 2) {
                    // throw new CacheValidateException(
                    // "查询map.entry需要传入两个Key.在key中以|分割" );
                    return (T) redisTemplate.opsForHash().entries(key);
                }
                return (T) redisTemplate.opsForHash().get(key.split("\\|")[0], key.split("\\|")[1]);

            case NONE:
                return null;
            default:
                log.error("CacheServiceImpl.get 异常");
                throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
    }

    @Override
    public <T> T get(String redisKey, String mapKey) {
        return null;
    }

    @Override
    public Boolean exist(String key) {
        CacheValidator.addValidate(key);
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        CacheValidator.addValidate(key);
        redisTemplate.delete(key);
    }

    @Override
    public Long incr(String key, int value, long timeout) {
        CacheValidator.addValidate(key);
        long newValue = redisTemplate.opsForValue().increment(key, value);
        // 设置过期时间
        if (timeout != 0 && !redisTemplate.expire(key, timeout, TimeUnit.SECONDS)) {
            log.error("CacheServiceImpl.incr 异常");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
        return newValue;
    }

    /**
     * 计数并在指定时间失效
     *
     * @param key
     * @param value
     * @param date
     * @return
     */
    @Override
    public Long incrAndExpireAt(String key, int value, final Date date) {
        CacheValidator.addValidate(key);
        long newValue = redisTemplate.opsForValue().increment(key, value);
        // 设置过期时间
        if (date != null && !redisTemplate.expireAt(key, date)) {
            log.error("CacheServiceImpl.incrAndExpireAt 异常");
            throw PlugException.INSTANCE(ErrorConstant.FAILURE, I18nConstant.FAILURE, "系统异常 !");
        }
        return newValue;
    }

    @Override
    public Boolean setnx(String key, String value) {
        return addIfAbsent(key,value);
    }

    /**
     * expire:设置失效期. <br/>
     *
     * @param key
     * @param seconds
     * @return
     * @author William
     * @since JDK 1.7
     */
    @Override
    public Boolean expire(final String key, final int seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 删除指令
     *
     * @param key
     */
    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * expireAt:(在某一个时间点失效). <br/>
     *
     * @param key
     * @param date
     * @return
     * @author William
     * @since JDK 1.7
     */
    @Override
    public Boolean expireAt(String key, final Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * getExpire:(获取失效时间). <br/>
     *
     * @param key
     * @param timeUnit
     * @return
     * @author William
     * @since JDK 1.7
     */
    @Override
    public Long getExpire(String key, final TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }


}
