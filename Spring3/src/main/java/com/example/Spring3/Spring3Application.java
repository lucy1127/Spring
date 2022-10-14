package com.example.Spring3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.example.Spring3.*")
public class Spring3Application {
	public static void main(String[] args) {
		SpringApplication.run(Spring3Application.class, args);
	}
}
