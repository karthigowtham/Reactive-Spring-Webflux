package io.highway.api;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Vehicle {
       
    @Id
	private String  carNumber;
    
    private Integer speed;
    private String  color;
    private String  type;
    private LocalDateTime time;
        
    public Vehicle(String carNumber,Integer speed, String  color, String type) {
    	this.carNumber = carNumber;
    	this.speed = speed;
    	this.color = color;
    	this.type = type;
    	this.time = LocalDateTime.now();
    }
    
    public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	} 
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return String.format("[Number=%s, Type=%s, Speed=%s, Color=%s, Time=%s]",	carNumber, type, speed, color, time);
	}
	
    
    	
}

