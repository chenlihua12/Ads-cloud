package com.honor.common.sso.ControllerAdvice;

import com.honor.common.base.dto.UserDto;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SSoControllerAdvice {

    @ModelAttribute
        public UserDto newHeadersDto(HttpServletRequest request) {
        return (UserDto) request.getAttribute("currentUser");
    }
}
