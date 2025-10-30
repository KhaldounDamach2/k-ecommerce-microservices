package com.dam.k_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantPhotoDto {

	private Long id;
	
	@NotBlank(message = "URL cannot be blank")
    private String url;

	private String altText;

	@NotNull(message = "Position cannot be null")
	private Integer position;
	
	ProductVariantDto productVariantDto; // Assuming this is the correct reference to ProductVariantDto

}
