package com.honor.common.sso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "sso")
@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SSOPropertiesDto {


    private String tokenName = "Authorization";
    private String app = "default";
    private Integer expired = 3600;
    /**
     * 排除登录校验拦截地址
     */
    private String[] excludeLoginPathPatterns;

    /**
     * 排除多点登录校验
     */
    private String[] excludeMultipointLoginPathPatterns;
}
