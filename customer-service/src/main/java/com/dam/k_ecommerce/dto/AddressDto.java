package com.dam.k_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

	
	@NotBlank(message = "Street cannot be blank")
    private String street;

	@NotBlank(message = "House number cannot be blank")
    private String houseNumber;

	@NotBlank(message = "City cannot be blank")
    private String city;

	@NotBlank(message = "Zip Code cannot be blank")
    private String zipCode;
	
	private LandDto land;
	
	
    	
}
