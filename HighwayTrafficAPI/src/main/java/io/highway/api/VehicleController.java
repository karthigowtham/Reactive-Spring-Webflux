package io.highway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VehicleController {
	
	WebClient webClient = WebClient.create("http://localhost:8083/analyze");
	
	@Autowired
	private VehicleRepository repository;
	
	@GetMapping(value="/findByNumber/{number}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Mono<Vehicle> findVehicle(@PathVariable("number") String carNumber) {		
		return repository.findById(carNumber);
		
	}
	
	@GetMapping(value="/findByType/{type}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Flux<Vehicle> findVehicles(@PathVariable("type") String type) {		
		return repository.findAllBytype(type);
		
	}
	
	@GetMapping(value="/live", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Flux<Vehicle> getLiveVehicles() {
		return repository.findAllWithTailableCursorBy();
		
	}
	
	@GetMapping(value="/live", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Flux<VehicleDetails> getVehiclesDetails(@RequestParam("speed") Integer speed) {		
		System.out.println("VEHICLE-Thread:" +Thread.currentThread().getName());
		
		Flux<Vehicle> feed = repository.findAllWithTailableCursorBy();
		
		Flux<VehicleDetails> details = feed.filter(vehicle -> vehicle.getSpeed() > speed)
											.flatMap(vehicle -> {
												 return Flux.just( new VehicleDetails(vehicle, getRisk(vehicle.getCarNumber())));
												});							
																
		return details;
		
	}
	
	
	private Mono<String> getRisk(String number) {
		
		return webClient.get().uri("/{number}", number).retrieve().bodyToMono(String.class);			
	}
	
}
