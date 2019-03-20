package io.highway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import io.highway.api.HighwayTrafficSimulator;
import io.highway.api.VehicleRepository;

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
			/*repository.deleteAll().subscribe(null, 
					err -> System.out.println("Error While Deleting:"+err), 
					() -> {*/
			
				int count = 0;
				
				while(count <= 20) {
					repository.save(HighwayTrafficSimulator.getVehicle()).subscribe(vehicle -> System.out.println("INSERTED:"+vehicle));
					
					count++;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				
				/*});*/
		};
	}
		
}
