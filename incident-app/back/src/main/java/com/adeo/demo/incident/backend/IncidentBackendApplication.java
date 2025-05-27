package com.adeo.demo.incident.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adeo.demo.incident.backend.persistence")
public class IncidentBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentBackendApplication.class, args);
    }
}
