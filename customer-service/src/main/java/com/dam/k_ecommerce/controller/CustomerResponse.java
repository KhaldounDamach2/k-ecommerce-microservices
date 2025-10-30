package com.dam.k_ecommerce.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

	private Long id;
	private String firstName;
	private String secondName;
	private String email;
	private String street;
	private String houseNumber;
	private String city;
	private String zipCode;
	private String landName;
	
}
