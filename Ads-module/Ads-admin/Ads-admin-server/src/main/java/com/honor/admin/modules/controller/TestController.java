package com.honor.admin.modules.controller;

import com.honor.common.base.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试")
@RequestMapping("/inner")
public class TestController {

    @ApiOperation("测试")
    @GetMapping("/test")
    public String test(UserDto userDto){
        return "test111";
    }
}

