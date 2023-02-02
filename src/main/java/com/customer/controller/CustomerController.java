package com.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.jwtSecurity.JwtUtil;
import com.customer.model.Customer;
import com.customer.model.UserRequest;
import com.customer.model.UserResponse;
import com.customer.services.CustomerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private JwtUtil util;

	@GetMapping("/home")
	// @RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "This is Home Page";
	}

	@GetMapping("/customers")
	public List<Customer> getCustomer() {
		return custService.getAllCustomer();
	}

	@GetMapping("/customers/{customerId}")
	public Customer getSingleCustomer(@PathVariable int customerId) {

		return custService.getSingleCustomer(customerId);

	}

	@PostMapping(path = "/addcustomer", consumes = "application/json")
	public ResponseEntity<HttpStatus> addCustomer(@RequestBody Customer customer) {

		try {
			custService.addCustomer(customer);
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updatecustomer/{customerId}")
	public ResponseEntity<HttpStatus> updateCustomer(@RequestBody Customer customer, @PathVariable int customerId) {

		try {
			custService.updateCustomer(customer, customerId);
			System.out.println("Update Successfull");
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Update Unsuccessfull");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customers/{custID}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable int custID) {

		try {
			custService.deleteCustomer(custID);
			System.out.println("Deleted Successfully");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Deletion Unsuccessful");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
//	@PostMapping("/login")
//	public ResponseEntity<UserResponse>loginUser(@RequestBody UserRequest request){
//		String token= util.generateToken(request.getUsername());
//		
//		return ResponseEntity.ok(new UserResponse(token,"Sucess"));
//	}
	
	
	
	
	
	

}
