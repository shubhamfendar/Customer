package com.customer.services;

import java.util.List;

import com.customer.model.Customer;
import com.customer.model.ResponseData;
import com.customer.model.SignUpData;

public interface CustomerService {
	
	public List<Customer> getAllCustomer();
	
	public Customer getSingleCustomer(int customerId);
	
	public Customer addCustomer(Customer customer);
	
	public void updateCustomer(Customer customer,int customerId);
	
	public void deleteCustomer(int custID);

	

	public SignUpData signUp(SignUpData data);

	public ResponseData login(SignUpData log);

}
