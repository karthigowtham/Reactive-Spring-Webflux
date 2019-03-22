package io.highway.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class VehicleLegacyController {
		
	
	@GetMapping(value="/findByLegacy/{number}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public String findLegacyVehicle(@PathVariable("number") String carNumber) {
			
		final String uri = "http://localhost:8083/rest/{number}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("number", carNumber);
	     
	    RestTemplate restTemplate = new RestTemplate();
	    
	    System.out.println("NUMBER:"+carNumber+", VEHICLE-REST-Thread:" +Thread.currentThread().getName());	    
		
		return restTemplate.getForObject(uri, String.class, params);		
		
		
	}
	
}
