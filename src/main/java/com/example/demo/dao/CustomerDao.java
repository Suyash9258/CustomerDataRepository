package com.example.demo.dao;

import java.util.Optional;

import com.example.demo.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Slf4j
public class CustomerDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CustomerRepository custRepository;

	

	@Transactional
	public void saveCustomer(Customer cust) {
		log.info("DB call --->"+cust);
		custRepository.save(cust);
	}


	public Customer fetchDbdetails(long custId) {
		Optional<Customer> cust = custRepository.findById(custId);
		log.info("Customer Details retreived for custid--> " + custId+ "---> "+cust);

		return cust.orElseThrow(() ->

				new CustomerNotFoundException("No customer found with ID: " + custId)
		);
	}

}
