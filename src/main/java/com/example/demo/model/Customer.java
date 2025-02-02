package com.example.demo.model;

import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.*;
import lombok.*;



@Table(name = "customers")
@Entity
@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custId;
	
	@Column(name = "first_name" )
	private String firstName; 

	@Column(name = "last_name" )
	private String lastName;
	
	@Column(name = "date_of_birth" )
	private Date dateOfBirth;


}
