package com.honor.common.sso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtConfigDto {
    private String jwtSecretKey = "123456";
    private String uidAesSecretKey = "123456";
    private Integer expired = 1000;
}
