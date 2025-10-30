package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDto {

	private Long id;

	@NotBlank(message = "SKU cannot be blank")
	private String sku;

	@NotNull(message = "Price cannot be null")
	private BigDecimal price;

	@NotNull(message = "Stock cannot be null")
	private Integer stock;

	@PastOrPresent(message = "Manufacture date cannot be in the future")
    private LocalDate manufDate;
	
	private String model;
	
	ProductDto productDto;
}
