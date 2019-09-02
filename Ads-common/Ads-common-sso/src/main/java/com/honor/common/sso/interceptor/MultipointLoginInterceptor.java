package com.honor.common.sso.interceptor;

import com.honor.common.sso.dto.SSOPropertiesDto;
import com.honor.common.sso.enums.OsTypeEnum;
import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.RedisConstant;
import com.honor.common.base.exception.CommonException;
import com.honor.common.redis.service.ICacheService;
import com.honor.common.sso.util.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MultipointLoginInterceptor extends HandlerInterceptorAdapter {

    private SSOPropertiesDto ssoPropertiesDto;

    @Autowired
    private ICacheService cacheService;

    public MultipointLoginInterceptor(SSOPropertiesDto ssoPropertiesDto) {
        this.ssoPropertiesDto = ssoPropertiesDto;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //移动端不进行多点校验
        if (OsTypeEnum.isMobile(request)) {
            return super.preHandle(request, response, handler);
        }

        // 登录参数校验
        String accessToken = request.getHeader(ssoPropertiesDto.getTokenName());
        if (StringUtils.isEmpty(accessToken)) {
            return super.preHandle(request, response, handler);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        String mobile = cacheService.get(ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_KEY + accessToken);

        if (mobile != null) {
            String loginToken = cacheService.get(ssoPropertiesDto.getApp() + RedisConstant.SESSION_MOBILE_LOGIN_KEY + mobile);
            if (loginToken != null && !loginToken.equals(accessToken)) {

                // 只会提醒一次
                cacheService.del(ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_KEY + accessToken);
                cacheService.del(ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_USER_KEY + accessToken);
                throw CommonException.INSTANCE(ErrorConstant.MULTIPLE_LOGIN_FAIL, "您已在其它设备登录,请重新登录");
            }
        }

        return super.preHandle(request, response, handler);
    }
}