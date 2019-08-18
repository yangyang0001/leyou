package com.inspur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * User: YANG
 * Date: 2019/8/7-14:33
 * Description: No Description
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.inspur.**.mapper"})
public class LeyouItemServiceApplication {
	public static void main(String[] args){
		SpringApplication.run(LeyouItemServiceApplication.class, args);
	}
}
