//package com.example.demo.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.demo.model.Employee;
//import com.example.demo.service.DemoService;
//
//@SpringBootTest
//public class DemoControllerTest {
//	
//	@InjectMocks
//	DemoController controller;
//
//	@Mock
//	DemoService service;
//	
//	
//	
//	@Test
//	public void testStatus() {
//			
//		Employee emp = new Employee();
//		String s1= controller.getStatus(setEmployee());
//	//	Mockito.when(service.updateEmployeeSalary(emp)).doNothing();
//		Assertions.assertEquals("Sucesss!!!!", s1);
//	}
//	
//	 Employee setEmployee()
//	{
//		 Employee e1 = new Employee();
//		 e1.setCity("Pune");
//		 e1.setEmpid(1);
//		 e1.setCity("Suyash");
//		 e1.setSalary(50000);
//		 return e1; 
//	}
//}
