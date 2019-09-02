package com.honor.common.sso.javaConfig;


import com.honor.common.sso.dto.JwtConfigDto;
import com.honor.common.sso.dto.SSOPropertiesDto;
import com.honor.common.sso.interceptor.LoginInterceptor;
import com.honor.common.sso.interceptor.MultipointLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置
 */
@Configuration
@Slf4j
@ConditionalOnProperty(
        prefix = "sso",
        value = {"enabled"},
        matchIfMissing = false
)
public class SSOConfig implements WebMvcConfigurer {

    @Resource
    private SSOPropertiesDto ssoPropertiesDto;
    @Resource
    private JwtConfigDto jwtConfigDto;

    public SSOConfig() {
    }

    @Bean
    public MultipointLoginInterceptor multipointLoginInterceptor() {
        log.info("==============================多点登录拦截器启动===============================");
        return new MultipointLoginInterceptor(ssoPropertiesDto);
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        log.info("==============================单点登录拦截器启动===============================");
        return new LoginInterceptor(ssoPropertiesDto, jwtConfigDto);
    }

    public void addInterceptors(InterceptorRegistry registry) {

        // 多点校验必须放在前，不然逻辑会有问题
        registry.addInterceptor(this.multipointLoginInterceptor())
                .order(0)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger**/**", "/e/swagger-ui.html", "/inner/**")
                .excludePathPatterns(ssoPropertiesDto.getExcludeMultipointLoginPathPatterns() == null ? new String[]{""} : ssoPropertiesDto.getExcludeMultipointLoginPathPatterns());


        registry.addInterceptor(this.loginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger**/**", "/exchange/swagger-ui.html", "/inner/**")
                .excludePathPatterns(ssoPropertiesDto.getExcludeLoginPathPatterns() == null ? new String[]{""} : ssoPropertiesDto.getExcludeLoginPathPatterns());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                .maxAge(3600);
    }
}

