package com.plainprog.duobk_web_service;

import com.plainprog.duobk_web_service.controllers.DictionaryController;
import com.plainprog.duobk_web_service.services.DictionaryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters=false)  // Disable component scanner
public class DuobkWebServiceApplication {
	public static final String DICTIONARY_SERVICE_URL = "http://dictionary-service";

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
	public DictionaryService dictionaryService() {
		return new DictionaryService(DICTIONARY_SERVICE_URL);
	}

	@Bean
	public DictionaryController accountsController() {
		return new DictionaryController(dictionaryService());
	}
}
