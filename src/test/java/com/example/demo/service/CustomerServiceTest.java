package com.example.demo.service;

import static org.mockito.Mockito.*;

import com.example.demo.dao.CustomerDao;
import com.example.demo.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.demo.exception.CustomerNotFoundException;

import com.example.demo.model.Customer;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import java.sql.Date;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao custDaoImpl;

    private Customer validCustomer;
    private Customer invalidCustomer;

    @BeforeEach
    void setUp() throws ParseException {
        validCustomer = new Customer();
        validCustomer.setCustId(101L);
        validCustomer.setFirstName("John");
        validCustomer.setLastName("Doe");
        validCustomer.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15"));

        invalidCustomer = new Customer();
        invalidCustomer.setCustId(102L);
        invalidCustomer.setFirstName(""); // Invalid first name
        invalidCustomer.setLastName("Smith");
        invalidCustomer.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"));
    }
    @Test
    void testSaveCustomerDetails_Success() throws ValidationException {
        // Arrange
        Mockito.doNothing().when(custDaoImpl).saveCustomer(validCustomer);

        // Act
        customerService.saveCustomerDetails(validCustomer);

        // Assert
        Mockito.verify(custDaoImpl, Mockito.times(1)).saveCustomer(validCustomer);
    }

    @Test
    void testSaveCustomerDetails_NullCustomer() throws ValidationException {
        // Act
        customerService.saveCustomerDetails(null);

        // Assert
        Mockito.verify(custDaoImpl, Mockito.never()).saveCustomer(Mockito.any());
    }
    @Test
    void testSaveCustomerDetails_InvalidCustomer() {
        // Act & Assert
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> {
            customerService.saveCustomerDetails(invalidCustomer);
        });

        Assertions.assertEquals(null, exception.getMessage());
        Mockito.verify(custDaoImpl, Mockito.never()).saveCustomer(Mockito.any());
    }

    @Test
    void testValidateCustomerDetails_Success() throws ValidationException {
        // Act
        boolean result = customerService.validateCustomerDetails("John", "Doe", validCustomer.getDateOfBirth());

        // Assert
        Assertions.assertTrue(result);
    }
    @Test
    void testValidateCustomerDetails_InvalidFirstName() {
        // Act & Assert
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> {
            customerService.validateCustomerDetails("", "Doe", validCustomer.getDateOfBirth());
        });

        Assertions.assertEquals(null, exception.getMessage());
    }

    @Test
    void testValidateCustomerDetails_InvalidLastName() {
        // Act & Assert
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> {
            customerService.validateCustomerDetails("John", "", validCustomer.getDateOfBirth());
        });

        Assertions.assertEquals(null, exception.getMessage());
    }
    @Test
    void testValidateCustomerDetails_InvalidDateOfBirth() {
        // Act & Assert
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> {
            customerService.validateCustomerDetails("John", "Doe", null);
        });

        Assertions.assertEquals(null, exception.getMessage());
    }

    @Test
    void testFetchCustomerDetails_Success() throws Exception {
        // Arrange
        Mockito.when(custDaoImpl.fetchDbdetails(101L)).thenReturn(validCustomer);

        // Act
        Customer result = customerService.fetchCustomerDetails(101L);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(101L, result.getCustId());
        Assertions.assertEquals("John", result.getFirstName());
    }
    @Test
    void testFetchCustomerDetails_NotFound() {
        // Arrange
        Mockito.when(custDaoImpl.fetchDbdetails(102L)).thenThrow(new CustomerNotFoundException("No customer found with ID: 102"));

        // Act & Assert
        Exception exception = Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.fetchCustomerDetails(102L);
        });

        Assertions.assertEquals("No customer found with ID: 102", exception.getMessage());
    }
}
