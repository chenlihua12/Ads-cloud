package com.honor.fegin.hystrix;

import com.honor.fegin.AdminFeginApi;
import org.springframework.stereotype.Component;

@Component
public class AdminFeginApiHystrix implements AdminFeginApi {
    @Override
    public String test() {
        return null;
    }
}
