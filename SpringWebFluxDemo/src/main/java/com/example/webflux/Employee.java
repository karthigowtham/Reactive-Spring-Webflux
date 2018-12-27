package com.example.webflux;

public class Employee {
	
	public Employee(String id, String name, String dept, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.address = address;
	}
	
	private String id;
	private String name;
	private String dept;
	private Address address;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
    public String toString() {
		StringBuilder builder = new StringBuilder(name);
		builder.append(",");
		builder.append(id);
		builder.append(",");
		builder.append(address.toString());
		return builder.toString();
		
	}

}
