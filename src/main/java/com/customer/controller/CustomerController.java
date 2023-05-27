package com.customer.controller;

import java.sql.Connection;
import java.util.List;

import javax.management.RuntimeErrorException;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.customer.config.SessionMgm;
import com.customer.dao.DBConfig;
import com.customer.model.Customer;
import com.customer.model.ResponseData;
import com.customer.model.SignUpData;
import com.customer.services.CustomerService;
import com.customer.services.CustomerServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {

	@Autowired
	private CustomerService custService;

	@Autowired
	private SessionMgm sessionMgm;

	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	private SessionMgm sessionMgm2;

	Connection conn = DBConfig.create();

	@GetMapping("/home")
	public String index() {
		return "This is Home Page";
	}

	@GetMapping("/customers")
	public List<Customer> getCustomer(@RequestHeader("Authorization") String uid) {
		try {
			sessionMgm2.getSession(uid);
			if (customerServiceImpl.validateUID(uid)) {
				return custService.getAllCustomer();
			} else {
				throw new RuntimeException("Invalid Token..Please Relogin");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());

		}
	}

	@GetMapping("/customers/{customerId}")
	public Customer getSingleCustomer(@PathVariable int customerId, @RequestHeader("Authorization") String uid) {
		try {
			sessionMgm2.getSession(uid);
			if (customerServiceImpl.validateUID(uid)) {
				return custService.getSingleCustomer(customerId);
			} else {
				throw new Exception("Invalid token..!!");
			}
		} catch (Exception e) {
			throw new RuntimeErrorException(null, e.getMessage());
		}
	}

	@PostMapping(path = "/addcustomer", consumes = "application/json")
	public ResponseEntity<HttpStatus> addCustomer(@RequestBody Customer customer,
			@RequestHeader("Authorization") String uid) throws Exception {

		try {
			sessionMgm2.getSession(uid);
			if (customerServiceImpl.validateUID(uid)) {
				custService.addCustomer(customer);
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			} else {
				System.out.println("inValid Token");
				throw new Exception("Invalid token..!!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@PutMapping("/updatecustomer/{customerId}")
	public ResponseEntity<HttpStatus> updateCustomer(@RequestBody Customer customer, @PathVariable int customerId,
			@RequestHeader("Authorization") String uid) throws Exception {

		try {
			sessionMgm2.getSession(uid);
			if (customerServiceImpl.validateUID(uid)) {
				custService.updateCustomer(customer, customerId);
				System.out.println("Update Successfull");
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			} else {
				throw new Exception("Invalid Token..!!");
			}
		} catch (Exception e) {
			System.out.println("Update Unsuccessfull");
			throw new Exception(e.getMessage());
		}

	}

	@DeleteMapping("/customers/{custID}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable int custID,
			@RequestHeader("Authorization") String uid) throws Exception {

		try {
			sessionMgm2.getSession(uid);
			if (customerServiceImpl.validateUID(uid)) {
				custService.deleteCustomer(custID);
				System.out.println("Deleted Successfully");
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				throw new Exception("Invalid Token..!!");
			}
		} catch (Exception e) {
			System.out.println("Deletion Unsuccessful");
			throw new Exception(e.getMessage());
		}

	}

	@PostMapping(path = "/signup", consumes = "application/json")
	public ResponseEntity<ResponseData> Signup(@RequestBody SignUpData data) {

		try {
			custService.signUp(data);
			ResponseData rs = new ResponseData();
			rs.setHttpStatus(HttpStatus.OK);
			return new ResponseEntity<ResponseData>(rs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<ResponseData> Login(@RequestBody SignUpData log) {
		try {
			ResponseData login = custService.login(log);
//			ResponseData rs = new ResponseData();
//			if(login!=null) {
//				rs.setHttpStatus(HttpStatus.OK);			
//			}else {
//				rs.setHttpStatus(HttpStatus.BAD_REQUEST);	
//			}
			return new ResponseEntity<ResponseData>(login, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
