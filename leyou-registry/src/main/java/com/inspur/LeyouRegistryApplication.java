package com.inspur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * User: YANG
 * Date: 2019/8/7-12:03
 * Description: No Description
 */
@SpringBootApplication
@EnableEurekaServer
public class LeyouRegistryApplication {

	public static void main(String[] args){
		SpringApplication.run(LeyouRegistryApplication.class, args);
	}
}
