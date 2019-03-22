package io.analyzer.rest;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.SyslogOutputStream;

@RestController
@RequestMapping("/rest")
public class LegacyController {
	
	private static String[] RISKS = { "NO", "LOW", "HIGH", "WANTED", "INTERMEDIATE" };
	
	@GetMapping("/{number}")
	public String getRisk(@PathVariable("number") String number) throws InterruptedException {
		
		System.out.println("NUMBER:"+number+", REST-RISK-Thread:" +Thread.currentThread().getName());

		Thread.sleep(4000);
		String risk = RISKS[ThreadLocalRandom.current().nextInt(0, 4)];
		System.out.println("RISK Identified as:"+risk+", for:"+number);
			
		return risk;
		
	}

}
