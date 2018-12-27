package com.example.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class SpringWebFluxDemoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxDemoRestApplication.class, args);
	}
}
