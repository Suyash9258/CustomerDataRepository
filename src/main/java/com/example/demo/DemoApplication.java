package com.example.demo;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		List<Integer> list = Arrays.asList(10,15,8,49,25,98,98,32,15);
		
		// list.stream().sorted(Collections.reverseOrder()).forEach(System.out::println);
	//	System.out.println("Count of passsed students  "+);
		
	}

}
