package io.highway.reactive;

import reactor.core.publisher.Mono;

public class VehicleDetails {

	private Vehicle vehicle;
	private Mono<String> riskLevel;
	
	public VehicleDetails(Vehicle vehicle, Mono<String> riskLevel) {
		this.vehicle = vehicle;
		this.riskLevel = riskLevel;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public Mono<String> getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(Mono<String> riskLevel) {
		this.riskLevel = riskLevel;
	}
}
