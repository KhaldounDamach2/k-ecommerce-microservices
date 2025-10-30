package com.dam.k_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.dam.k_ecommerce.webClients")
@EnableJpaAuditing
public class ProductServiceApplication {

	public static void main(String[] args) {
		//System.setProperty("eureka.client.serviceUrl.defaultZone", "http://discovery-service:8761/eureka/");
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
