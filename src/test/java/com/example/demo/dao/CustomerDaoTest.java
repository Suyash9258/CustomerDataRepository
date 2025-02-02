package com.example.demo.dao;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class CustomerDaoTest {

    @InjectMocks
    private CustomerDao customerDao;

    @Mock
    private CustomerRepository custRepository;

    private Customer validCustomer;

    @BeforeEach
    void setUp() {
        validCustomer = new Customer();
        validCustomer.setCustId(101L);
        validCustomer.setFirstName("John");
        validCustomer.setLastName("Doe");
        validCustomer.setDateOfBirth(new Date());
    }

    @Test
    @DisplayName("Test saving a customer successfully")
    void testSaveCustomer_Success() {
        // Arrange
        Mockito.when(custRepository.save(validCustomer)).thenReturn(validCustomer);

        // Act
        customerDao.saveCustomer(validCustomer);

        // Assert
        Mockito.verify(custRepository, Mockito.times(1)).save(validCustomer);
    }


    @Test
    @DisplayName("Test fetching customer details successfully")
    void testFetchDbDetails_Success() {
        // Arrange
        Mockito.when(custRepository.findById(101L)).thenReturn(Optional.of(validCustomer));

        // Act
        Customer result = customerDao.fetchDbdetails(101L);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(101L, result.getCustId());
        Assertions.assertEquals("John", result.getFirstName());
        Mockito.verify(custRepository, Mockito.times(1)).findById(101L);
    }

    @Test
    @DisplayName("Test fetching customer details - Not Found")
    void testFetchDbDetails_CustomerNotFound() {
        // Arrange
        Mockito.when(custRepository.findById(102L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerDao.fetchDbdetails(102L);
        });

        Assertions.assertEquals("No customer found with ID: 102", exception.getMessage());
        Mockito.verify(custRepository, Mockito.times(1)).findById(102L);
    }
}