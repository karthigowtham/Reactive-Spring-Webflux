package com.example.webflux;

public class EmployeeCreationEvent {
	
    private String employeeId;
    private String creationTime;
    
    public EmployeeCreationEvent(String employeeId, String creationTime) {
        super();
        this.employeeId = employeeId;
        this.creationTime = creationTime;
    }

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
}