package com.honor.common.sso.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.honor.common.sso.dto.JwtConfigDto;
import com.honor.common.sso.dto.SSOPropertiesDto;
import com.honor.common.sso.enums.OsTypeEnum;
import com.honor.common.sso.util.HttpContextUtils;
import com.honor.common.sso.util.JwtUtils;
import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.RedisConstant;
import com.honor.common.base.dto.UserDto;
import com.honor.common.base.exception.CommonException;
import com.honor.common.redis.service.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private SSOPropertiesDto ssoPropertiesDto;
    private JwtConfigDto jwtConfigDto;
    @Autowired
    private ICacheService cacheService;

    public LoginInterceptor(SSOPropertiesDto ssoPropertiesDto, JwtConfigDto jwtConfigDto) {
        this.ssoPropertiesDto = ssoPropertiesDto;
        this.jwtConfigDto = jwtConfigDto;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录参数校验
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())){
            response.setStatus(HttpServletResponse.SC_OK);
            return super.preHandle(request, response, handler);
        }
        String accessToken = request.getHeader(ssoPropertiesDto.getTokenName());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        if (StringUtils.isEmpty(accessToken)) {
            throw CommonException.INSTANCE(ErrorConstant.LOGIN_INFO_NOT_EXIST, "您还未登录,请先登录");
        }

        //移动端登录JWT   PC端用单点登录
        UserDto user;
        if (OsTypeEnum.isMobile(request)) {
            try {
                user = JwtUtils.verifyToken(accessToken, this.jwtConfigDto.getJwtSecretKey(), this.jwtConfigDto.getUidAesSecretKey());
            } catch (SignatureVerificationException e) {
                log.error("JwtLoginInterceptor.preHandle.error={}", e.getMessage(), e);
                throw CommonException.INSTANCE(ErrorConstant.LOGIN_INFO_MODIFICATION, "登录信息被篡改！");
            } catch (TokenExpiredException e) {
                log.error("JwtLoginInterceptor.preHandle.error={}", e.getMessage(), e);
                throw CommonException.INSTANCE(ErrorConstant.LOGIN_INFO_EXPIRED, "登录信息已过期！");
            } catch (Exception e) {
                log.error("JwtLoginInterceptor.preHandle.error={}", e.getMessage(), e);
                throw CommonException.INSTANCE(ErrorConstant.LOGIN_INFO_NOT_EXCEPTION, "登录信息解析异常！");
            }
        } else {
            user = cacheService.get(ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_USER_KEY + accessToken);
            if (user == null) {
                throw CommonException.INSTANCE(ErrorConstant.LOGIN_INFO_NOT_EXIST, "您还未登录,请先登录");
            }

            request.setAttribute("currentUser", user);
            // 刷新失效时间
            cacheService.add(ssoPropertiesDto.getApp() + RedisConstant.SESSION_MOBILE_LOGIN_KEY + user.getLoginName(), accessToken, ssoPropertiesDto.getExpired());
            cacheService.add(ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_KEY + accessToken, user.getLoginName(), ssoPropertiesDto.getExpired());
            cacheService.add(ssoPropertiesDto.getApp() + RedisConstant.SESSION_TOKEN_LOGIN_USER_KEY + accessToken, user, ssoPropertiesDto.getExpired());
        }
        request.setAttribute("currentUser", user);
        return super.preHandle(request, response, handler);
    }
}
