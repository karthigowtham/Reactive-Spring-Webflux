package io.highway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.highway.reactive.HighwayTrafficSimulator;
import io.highway.reactive.VehicleRepository;

@SpringBootApplication
public class HighwayTrafficApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(HighwayTrafficApiApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HighwayTrafficApiApplication.class);
	}
	

	@Bean
	public CommandLineRunner storeVehicles(VehicleRepository repository) {
		
		return args -> {
			
			//WebClient webClient = WebClient.create("http://localhost:8083/analyze");
			//webClient.get().uri("/{number}", "1234").retrieve().bodyToMono(String.class).subscribe(risk -> System.out.println("WORKING:"+risk));
			
			MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));
			DB db = mongoClient.getDB("test");
			 
			DBCollection collection;
			if (!db.collectionExists("vehicle")) {
			    BasicDBObject options = new BasicDBObject("capped", true);
			    options.append("size", 40960);
			    options.append("max", 500);
			    collection = db.createCollection("vehicle", options);
			} else {
			    db.getCollection("vehicle").drop();
			    
			    BasicDBObject options = new BasicDBObject("capped", true);
			    options.append("size", 40960);
			    options.append("max", 500);
			    collection = db.createCollection("vehicle", options);
			}
			
			/*repository.deleteAll().subscribe(null, 
					err -> System.out.println("Error While Deleting:"+err), 
					() -> {*/
			
				int count = 0;
				
				while(count <= 20) {
					repository.save(HighwayTrafficSimulator.getVehicle()).subscribe(vehicle -> System.out.println("INSERTED:"+vehicle));
					
					count++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				
				/*});*/
		};
	}
		
}
