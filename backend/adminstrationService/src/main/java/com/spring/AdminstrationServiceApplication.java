package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AdminstrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminstrationServiceApplication.class, args);
		System.out.println("***************************Department Service Started***************************");
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
