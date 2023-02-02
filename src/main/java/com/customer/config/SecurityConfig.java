package com.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.customer.services.CustomerService;

@Configuration

public class SecurityConfig{

	@Autowired
	private CustomerService customerService;
	
}
