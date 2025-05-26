package com.adeo.demo.payment.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adeo.demo.payment.backend.persistence")
public class PaymentBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentBackendApplication.class, args);
	}

}
