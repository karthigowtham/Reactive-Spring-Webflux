package com.example.webflux;

public class Address {
	
	public Address(String addressLine1, String city, String state, String pincode) {
		super();
		this.addressLine1 = addressLine1;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}
	
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String pincode;	
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	@Override
    public String toString() {
		StringBuilder builder = new StringBuilder(addressLine1);
		builder.append(",");
		builder.append(city);
		builder.append(",");
		builder.append(state);
		builder.append("-");
		builder.append(pincode);
		return builder.toString();
		
	}
	
	
}
