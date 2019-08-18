package com.inspur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class LeyouGatewayApplication {

	public static void main(String[] args){
		SpringApplication.run(LeyouGatewayApplication.class, args);
	}

}
