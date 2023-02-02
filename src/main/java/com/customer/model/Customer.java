package com.customer.model;

public class Customer {

	private int custId;
	private String name;
	private String city;
	private int age;
	private String category;

	public Customer() {
		super();
	}

	
	public Customer(int custId, String name, String city, int age, String category) {
		super();
		this.custId = custId;
		this.name = name;
		this.city = city;
		this.age = age;
		this.category = category;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", name=" + name + ", city=" + city + ", age=" + age + ", category="
				+ category + "]";
	}

}
