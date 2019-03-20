package io.analyzer.api;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/analyze")
public class RiskController {
	
	private static String[] RISKS = { "NO", "LOW", "HIGH", "WANTED", "INTERMEDIATE" };
	
	@GetMapping("/{number}")
	public Mono<String> getRisk(@PathVariable("number") String number) throws InterruptedException {
		
		System.out.println("RISK-Thread:" +Thread.currentThread().getName());
		System.out.println("Analyzing:" +number);
		Thread.sleep(3000);
		
		String risk = RISKS[ThreadLocalRandom.current().nextInt(0, 4)];
		Mono<String> mono = Mono.just(risk);
		
		return mono;
		
	}

}
