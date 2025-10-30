package com.dam.k_ecommerce.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	private Long id;

	@NotBlank(message = "Name cannot be blank")
	private String prodName;

	private String prodDescrip;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private CategoryDto categoryDto;
	
	private ClientDto clientDto;

	

}
