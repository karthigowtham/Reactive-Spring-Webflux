package com.example.webflux;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Component
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
	private EmployeeRepository employeeRepository;
        
    @GetMapping("/get/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    private Mono<String> getEmployeeById(@PathVariable String id) {
    	System.out.println("INSIDE SERVER - get:"+System.currentTimeMillis());
    	System.out.println("SER-MONO: Thread - " + Thread.currentThread().getName());
       // return Mono.just(employeeRepository.get(1));
    	return Mono.just("RESPOSNSE SUCCESS");
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    private Flux<Object> getAllEmployees() {
    	System.out.println("INSIDE SERVER - getAll:"+System.currentTimeMillis());
    	System.out.println("SER-Flux: Thread - " + Thread.currentThread().getName());
        return Flux.create(fluxSink -> {
            int index = 1;
            while (index <= 5 ) {
                fluxSink.next(employeeRepository.get(index).toString());
                index++;
            }
        }).share();
    }

    @PostMapping("/update")
    @ResponseStatus(code = HttpStatus.OK)
    private Mono<String> updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.updateEmployee(employee);
    }
    
    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    private Mono<String> createEmployee(@RequestBody Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

}