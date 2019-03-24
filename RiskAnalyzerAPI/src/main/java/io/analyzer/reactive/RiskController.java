package io.analyzer.reactive;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/risk")
public class RiskController {

	private static String[] RISKS = { "NO", "LOW", "HIGH", "WANTED", "INTERMEDIATE" };

	@GetMapping("/{number}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<String> getRisk(@PathVariable("number") String number) throws InterruptedException {

		System.out.println("NUMBER:" + number + ", RISK-Thread:" + Thread.currentThread().getName());

		Thread.sleep(10000);

		String risk = RISKS[ThreadLocalRandom.current().nextInt(0, 4)];
		System.out.println("RISK Identified as:" + risk + ", for:" + number);
		Mono<String> mono = Mono.just(risk);

		return mono;

	}

	@PostMapping("/store/{number}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<String> UpdateRisk(@PathVariable("number") String number) throws InterruptedException {

		System.out.println("NUMBER:" + number + ", STORE-Thread:" + Thread.currentThread().getName());

		Thread.sleep(6000);

		return Mono.just("Success");

	}

}
