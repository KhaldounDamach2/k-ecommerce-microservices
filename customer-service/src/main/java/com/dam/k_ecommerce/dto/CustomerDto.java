package com.dam.k_ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	private String secondName;
	
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;
	
	private AddressDto addressDto;
}
