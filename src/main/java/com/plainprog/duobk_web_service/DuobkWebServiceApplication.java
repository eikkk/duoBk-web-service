package com.plainprog.duobk_web_service;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class DuobkWebServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-service");
		SpringApplication.run(DuobkWebServiceApplication.class, args);
	}
	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public Gson getGson(){return new Gson();}

}
