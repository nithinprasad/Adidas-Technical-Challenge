package com.prasad.nithin.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PublicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicServiceApplication.class, args);
	}

}
