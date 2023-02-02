package com.customer.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.customer.model.Customer;

public interface CustomerService {
	
	public List<Customer> getAllCustomer();
	
	public Customer getSingleCustomer(int customerId);
	
	public Customer addCustomer(Customer customer);
	
	public void updateCustomer(Customer customer,int customerId);
	
	public void deleteCustomer(int custID);

}
