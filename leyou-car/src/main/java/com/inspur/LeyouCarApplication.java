package com.inspur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-21 12:24
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class LeyouCarApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouCarApplication.class, args);
    }
}
