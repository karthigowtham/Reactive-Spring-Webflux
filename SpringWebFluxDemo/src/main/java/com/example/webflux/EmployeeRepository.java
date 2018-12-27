package com.example.webflux;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeRepository {
    
    static Map<String,Employee> employeeData;

    static
    {
        employeeData = new HashMap<>();        
      
        employeeData.put("1",new Employee("1","Karthi", "Admin", new Address("1/25, new street", "Tambaram", "TN","600045")));
        employeeData.put("2",new Employee("2","Gowtham", "HR", new Address("2/254, Tamu street", "Chennai", "TN","600075")));
        employeeData.put("3",new Employee("3","Duclous", "Projects", new Address("1/47, Meenakshi temple", "Madurai", "TN","674045")));
        employeeData.put("4",new Employee("4","Gana", "Pantry", new Address("1/01, very old street", "Selam", "TN","600045")));
        employeeData.put("5",new Employee("5","Guru", "Projects", new Address("1/425, Modern street", "Aruppukottai", "TN","600045")));
    
    }
    
    public Employee get(int id) {
    	return employeeData.get(String.valueOf(id));
    }
    public Mono<Employee> findEmployeeById(String id)  {
    	/*try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	System.out.println("REPO - MONO");
        return Mono.just(employeeData.get(id));
    }
    
    public Flux<Employee> findAllEmployees() {
    	System.out.println("REPO - FLUX");
        return Flux.fromIterable(employeeData.values()).share();
    }
    
    public Mono<String> updateEmployee(Employee employee)
    {
    	String status = "ID NOT FOUND";
    	Employee existingEmployee=employeeData.get(employee.getId());
        if(existingEmployee!=null)        {
        	employeeData.put(employee.getId(), employee);
        	status = "SUCCESS";
        }
        return Mono.just(status);
    }
    
    public Mono<String> createEmployee(Employee employee)
    {
    	employeeData.put(employee.getId(), employee);
        return Mono.just("SUCCESSFULLY CREATED");
    }
}