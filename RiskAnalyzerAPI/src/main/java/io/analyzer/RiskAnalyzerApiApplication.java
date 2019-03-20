package io.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RiskAnalyzerApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(RiskAnalyzerApiApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RiskAnalyzerApiApplication.class);
	}

}
