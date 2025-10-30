package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(

		Long id,
		
		Long customertId,
		
		Long orderId,

		@NotNull(message = "Product ID cannot be null") 
		Long prodVariantId,

		@Positive(message = "Quantity must be a positive number") 
		int quantity,
		
		BigDecimal price

) {

}
