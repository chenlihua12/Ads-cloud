package com.honor.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public Map<String, String> defaultfallback() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "-1");
        map.put("msg", "服务器压力大,请稍后再试");
        map.put("data", null);
        return map;
    }
}