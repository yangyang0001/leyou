package com.inspur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * User: YANG
 * Date: 2019/8/7-12:18
 * Description: No Description
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class LeyouGatewayApplication {

	public static void main(String[] args){
		SpringApplication.run(LeyouGatewayApplication.class, args);
	}

}
