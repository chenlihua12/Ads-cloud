package com.honor.user;

import com.honor.fegin.AdminFeginApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.honor")
@EnableFeignClients(basePackages = "com.honor.fegin")
@Configuration
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

    @Autowired
    private AdminFeginApi adminFeginApi;

    @GetMapping("/test")
    public String test(){
        String test = adminFeginApi.test();
        return test;
    }


}
