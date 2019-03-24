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

	final ExecutorService executor = Executors.newFixedThreadPool(5);

	@Autowired
	private VehicleRepository repository;

	@GetMapping(value = "/findByNumber/{number}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Mono<Vehicle> findVehicle(@PathVariable("number") String carNumber) {
		return repository.findById(carNumber);

	}

	@GetMapping(value = "/findByType/{type}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Flux<Vehicle> findVehicles(@PathVariable("type") String type) {
		return repository.findAllBytype(type);

	}

	@GetMapping(value = "/live", produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Flux<Vehicle> getLiveVehicles() {
		return repository.findAllWithTailableCursorBy();

	}

	@GetMapping(value = "/liveBySpeed", produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Flux<Vehicle> getVehiclesDetails(@RequestParam("speed") Integer speed) throws InterruptedException {

		System.out.println("VEHICLE-MAIN:" + Thread.currentThread().getName());

		Flux<Vehicle> feed = repository.findAllWithTailableCursorBy();

		return feed.filter(vehicle -> vehicle.getSpeed() > speed).flatMap(vehicle -> {
			storeRisk(vehicle.getCarNumber());
			return Flux.just(vehicle);
		});

	}

	@GetMapping(value = "/riskByNumber/{number}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Mono<Object> analyzeVehicle(@PathVariable("number") String carNumber) {

		System.out.println("NUMBER:" + carNumber + ", VEHICLE-MAIN:" + Thread.currentThread().getName());

		/*
		 * getVehicleForTest.subscribe(vehicle -> {
		 * getRisk(vehicle.getCarNumber()).subscribe(str -> System.out.println(str),
		 * null, null); }, null, null);
		 * 
		 * return "SUCCESS";
		 */

		return getVehicleForTest(carNumber).subscribeOn(Schedulers.elastic())
				.map(vehicle -> getRisk(vehicle.getCarNumber()));

	}

	private Mono<String> getRisk(String number) {
		System.out.println("NUMBER:" + number + ", VEHICLE-CALLER:" + Thread.currentThread().getName());

		WebClient webClient = WebClient.create("http://localhost:8083/risk");
		return webClient.get().uri("/{number}", number).retrieve().bodyToMono(String.class);
	}

	private void storeRisk(String number) {
		System.out.println("NUMBER:" + number + ", VEHICLE-CALLER:" + Thread.currentThread().getName());

		WebClient webClient = WebClient.create("http://localhost:8083/risk/store");
		webClient.post().uri("/{number}", number).retrieve().bodyToMono(String.class).subscribe(null, null,
				() -> System.out.println(number + "-stored successfully:" + Thread.currentThread().getName()));

	}

	private Mono<Vehicle> getVehicleForTest(String carNumber) {
		return Mono.just(new Vehicle(carNumber, null, null, null));
	}

}
