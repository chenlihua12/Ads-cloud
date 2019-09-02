package com.honor.common.sso.util;

import com.honor.common.sso.dto.JwtConfigDto;
import com.honor.common.sso.dto.SSOPropertiesDto;
import com.honor.common.base.constant.RedisConstant;
import com.honor.common.base.dto.UserDto;
import com.honor.common.base.utils.IdUtils;
import com.honor.common.redis.service.ICacheService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class LoginUtils {

    @Resource
    private ICacheService cacheService;
    @Resource
    private SSOPropertiesDto ssoPropertiesDto;
    @Resource
    private JwtConfigDto jwtConfigDto;

    private static LoginUtils INSTANCE;

    public LoginUtils() {
        INSTANCE = this;
    }

    public static String login(UserDto userDto) {
        String token = IdUtils.uuid();
        INSTANCE.cacheService.add(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_MOBILE_LOGIN_KEY + userDto.getLoginName(), token, INSTANCE.ssoPropertiesDto.getExpired());
        INSTANCE.cacheService.add(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_KEY + token, userDto.getLoginName(), INSTANCE.ssoPropertiesDto.getExpired());
        INSTANCE.cacheService.add(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_USER_KEY + token, userDto, INSTANCE.ssoPropertiesDto.getExpired());
        return token;

    }

    public static String login(UserDto userDto, boolean isMobile) {
        if (isMobile) {
            return JwtUtils.createToken(userDto, INSTANCE.jwtConfigDto);
        }
        return login(userDto);
    }

    public static void logout(UserDto userDto) {
        String token = INSTANCE.cacheService.get(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_MOBILE_LOGIN_KEY + userDto.getLoginName());
        INSTANCE.cacheService.del(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_MOBILE_LOGIN_KEY + userDto.getLoginName());
        if (!StringUtils.isEmpty(token)) {
            INSTANCE.cacheService.del(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_KEY + token);
            INSTANCE.cacheService.del(INSTANCE.ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_USER_KEY + token);
        }
    }
}
