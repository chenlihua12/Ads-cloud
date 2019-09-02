package com.honor.fegin;

import com.honor.fegin.hystrix.AdminFeginApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ADMIN-SERVER",fallback = AdminFeginApiHystrix.class)
public interface AdminFeginApi {

    @GetMapping("inner/test")
    String test();

}
