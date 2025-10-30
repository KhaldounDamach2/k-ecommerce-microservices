package com.dam.k_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private Long id;

	@NotBlank(message = "Name cannot be blank")
	private String catName;

	private String catDescrip;

}
