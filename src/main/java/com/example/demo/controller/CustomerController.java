package com.example.demo.controller;


import com.example.demo.dao.CustomerRepository;
import com.example.demo.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerResponse;
import com.example.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	CustomerRepository custRepository;


	@PostMapping(value = "/details")
	public ResponseEntity<CustomerResponse> addCustomer(@RequestBody Customer custBody) throws ValidationException {
		CustomerResponse customerResponse = new CustomerResponse();
		try {
			customerService.saveCustomerDetails(custBody);
			customerResponse.setCustomerId(String.valueOf(custBody.getCustId()));
			customerResponse.setMessage("Customer details saved successfully");
			return new ResponseEntity<>(customerResponse, HttpStatus.OK);
		} catch (ValidationException e) {
			customerResponse.setCustomerId("customer validation Error for:" + String.valueOf(custBody.getCustId()));
			customerResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(customerResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			customerResponse.setCustomerId("Error:" + String.valueOf(custBody.getCustId()));
			customerResponse.setMessage(ex.getMessage());
			return new ResponseEntity<>(customerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/{custId}")
	public ResponseEntity<?> getCustomer(@PathVariable long custId) throws Exception {

		try {
			Customer customer = customerService.fetchCustomerDetails(custId);
			return ResponseEntity.ok(customer);
		} catch (CustomerNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Exception occurred: " + ex.getMessage());
		}

	}

//	@GetMapping("/fetchall")
//	public ResponseEntity<List<Customer>> getAllCustomers() throws Exception {
//
//
//		try {
//			List<Customer> response = custRepository.findAll();
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (IllegalArgumentException e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
}
