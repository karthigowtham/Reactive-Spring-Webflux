package io.highway.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RiskUtil {

	@Async
	public String getRisk(String carNumber) {

		final String uri = "http://localhost:8083/rest/{number}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("number", carNumber);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println("NUMBER:" + carNumber + ", VEHICLE-ASYNC:" + Thread.currentThread().getName());

		String result = restTemplate.getForObject(uri, String.class, params);
		System.out.println("RISK Received-" + result);

		return result;
	}
}
