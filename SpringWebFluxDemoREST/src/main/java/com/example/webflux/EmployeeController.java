package com.example.webflux;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.synchronoss.cloud.nio.stream.storage.Disposable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


public class EmployeeController {  
   
   
    private String test() {
    	    	
    	System.out.println("INSIDE CLIENT:"+Thread.currentThread().getName() +"," + Thread.activeCount());
    	WebClient client = WebClient.create("http://localhost:8082");     
   
            Mono<String> employeeMono = client.get()
                .uri("/employees/get/{id}", "001")
                .retrieve()
                .bodyToMono(String.class);
            
            System.out.println("CL - Mono1 called:"+System.currentTimeMillis());
            
            employeeMono.subscribe((employee) -> {
            	System.out.println("CL- Inside MONO1 SUB");
            	System.out.println("CL -MONO1 - name: " + employee);
            	
            	
            },
            		err -> System.out.println("MONO ERRORED:"+ err.getMessage()),
                    () -> System.out.println("MONO STOPPED"));
                     
            System.out.println("CL- - After Mono1:" +System.currentTimeMillis());
                       
           /*Mono<Employee> employeeMono1 = client.get()
                    .uri("/employees/get/{id}", "002")
                    .retrieve()
                    .bodyToMono(Employee.class);
            
            System.out.println("CL - Mono2 called:" +System.currentTimeMillis());
            */
            Flux<String> employeeFlux = client.get()
                .uri("/employees/getAll")
                .exchange()
                .publishOn(Schedulers.single())
                .flatMapMany(response -> response.bodyToFlux(String.class));
            
            employeeFlux.subscribe( (employee) -> {
            	System.out.println("CL -Inside FLUX");
            	System.out.println("CL - FLUX - name: " + employee);
            	//System.out.println("Cl - FLUX -address : " + employee.getAddress().toString());
            },
            err -> System.out.println("FLUX ERRORED:"+ err.getMessage()),
            () -> System.out.println("FLUX STOPPED"));
            
          /*  employeeFlux.subscribe( (employee) -> {
            	System.out.println("CL - Inside FLUX2");
            	System.out.println("CL - FLUX2 - name: " + employee.getName() + "," + employee.getId());
            });
            System.out.println("CL - After Flux:" +System.currentTimeMillis());*/
          /*  
            employeeMono1.subscribe((employee) -> {
            	System.out.println("CL -Inside MONO2");
            	System.out.println("CL -MONO2 - name: " + employee.getName() + "," + employee.getId());
            	System.out.println("CL -MONO2 -address : " + employee.getAddress().toString());
            	
            });*/
            
           /* employeeFlux.subscribe( (employee) -> {
            	System.out.println("CL - Inside FLUX3");
            	System.out.println("CL - FLUX3 - name: " + employee.getName() + "," + employee.getId());
            	System.out.println("Cl - FLUX3 -address : " + employee.getAddress().toString());
            });*/
    	
        return "success";
    }
    

}