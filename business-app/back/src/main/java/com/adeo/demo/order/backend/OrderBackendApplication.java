package com.adeo.demo.order.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adeo.demo.order.backend.persistence")
public class OrderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderBackendApplication.class, args);
	}

}
