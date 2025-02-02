package com.example.demo.service;

//import static org.mockito.ArgumentMatchers.nullable;

import java.net.http.HttpRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CustomerDao;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.Customer;
@Component
public class CustomerService {
	
	@Autowired
	private CustomerDao custDaoImpl;
	
	public void saveCustomerDetails(Customer custBody) throws ValidationException {
		
		System.out.println("To update in employee method");
		Boolean CustValidation   = false;
		
		if( null != custBody) {
			
			CustValidation = validateCustomerDetails(custBody.getFirstName(),custBody.getLastName(),custBody.getDateOfBirth());
			
			if(CustValidation) {
				custDaoImpl.saveCustomer(custBody);
			}
			
		}
		
	}

	private boolean validateCustomerDetails(String firstName, String lastName, Date dateOfBirth) throws ValidationException {
		System.out.println("Validation started ");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = dateFormat.format(dateOfBirth);
		
		if(firstName == null || firstName == "" ) {
			
			throw new ValidationException(HttpStatus.BAD_REQUEST,"validation failed for firstName "+firstName);
		}
        if(lastName == null || lastName == "" ) {
        	throw new ValidationException(HttpStatus.BAD_REQUEST,"validation failed for firstName "+lastName);
		}
        if(dateOfBirth == null || !isDateValid(stringDate))
        {
        	throw new ValidationException(HttpStatus.BAD_REQUEST,"validation failed for date Of Birth "+stringDate);
        }
		
		return true;
	}

	
	public static boolean isDateValid(String date)
	{
	        try {
	            DateFormat df = new SimpleDateFormat(date);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}

	public Customer fetchCustomerDetails(long custId) throws Exception  {


		Customer customerDetails = custDaoImpl.fetchDbdetails(custId);
		return customerDetails;

	}
	
	


}
