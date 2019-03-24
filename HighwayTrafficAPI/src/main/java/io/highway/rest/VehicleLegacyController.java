package io.highway.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleLegacyController {

	@Autowired
	RiskUtil util;

	@GetMapping(value = "/findByLegacy/{number}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public String findLegacyVehicle(@PathVariable("number") String carNumber) {

		System.out.println("NUMBER:" + carNumber + ", VEHICLE-MAIN:" + Thread.currentThread().getName());
		util.getRisk(carNumber);

		return "Success";
	}

}
