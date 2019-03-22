package io.highway.reactive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import reactor.core.scheduler.Schedulers;

@RestController
public class VehicleController {
			
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
	
	@GetMapping(value="/liveBySpeed", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Flux<VehicleDetails> getVehiclesDetails(@RequestParam("speed") Integer speed) {
		//ExecutorService executor = Executors.newFixedThreadPool(2);
		
		System.out.println("VEHICLE-Thread:" +Thread.currentThread().getName());
		
		Flux<Vehicle> feed = repository.findAllWithTailableCursorBy();
		
		Flux<VehicleDetails> details = feed.filter(vehicle -> vehicle.getSpeed() > speed)/*.subscribeOn(Schedulers.parallel())*/
											.flatMap(vehicle -> {
													System.out.println("NUMBER:"+vehicle.getCarNumber()+", VEHICLE-CALLER:" +Thread.currentThread().getName());
												 return Flux.just( new VehicleDetails(vehicle, getRisk(vehicle.getCarNumber())));
												});							
																
		return details;
		
	}
	
	@GetMapping(value="/riskByNumber/{number}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public String analyzeVehicle(@PathVariable("number") String carNumber) {		
		
		System.out.println("NUMBER:"+carNumber+", VEHICLE-Thread:" +Thread.currentThread().getName());
		
		//ExecutorService executor = Executors.newFixedThreadPool(2);
			
		/*repository.findById(carNumber).subscribeOn(Schedulers.fromExecutor(executor)).subscribe(vehicle-> {
										System.out.println("DATA:"+vehicle);
										System.out.println("VEHICLE-CALLER:" +Thread.currentThread().getName());
										System.out.println("RISK:"+getRisk(carNumber));
								});*/
		
		repository.findById(carNumber).subscribe(vehicle-> {
										System.out.println("DATA:"+vehicle);
										System.out.println("NUMBER:"+vehicle.getCarNumber()+", VEHICLE-CALLER:" +Thread.currentThread().getName());
										System.out.println("RISK:"+getRisk(vehicle.getCarNumber()));
								}, err -> System.out.println("ERROR:"+err), () -> System.out.println("COMPLETED"));
				
		
				
		return "SUCCESS";
		
	}
		
	private Mono<String> getRisk(String number) {		
		WebClient webClient = WebClient.create("http://localhost:8083/analyze");
		return webClient.get().uri("/{number}", number).retrieve().bodyToMono(String.class);			
	}

}
