package io.highway.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface VehicleRepository extends ReactiveMongoRepository<Vehicle,String>{
	
	Flux<Vehicle> findAllBytype(String type);
	
	@Tailable
	Flux<Vehicle> findAllWithTailableCursorBy();
	
	

}
