package com.honor.common.base.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private Long userId;
    private String areaCode;
    private String loginName;

    public UserDto() {
    }

    public UserDto(Long userId, String loginName) {
        this.userId = userId;
        this.loginName = loginName;
    }

    public UserDto(Long userId, String areaCode, String loginName) {
        this.userId = userId;
        this.areaCode = areaCode;
        this.loginName = loginName;
    }
}

