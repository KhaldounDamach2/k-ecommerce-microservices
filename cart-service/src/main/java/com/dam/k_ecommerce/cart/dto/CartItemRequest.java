package com.dam.k_ecommerce.cart.dto;

import java.math.BigDecimal;

import com.dam.k_ecommerce.model.Cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequest(
		
		
		
		@NotNull(message = "Product ID cannot be null")
		Long prodVariantId,
		
		

		@NotNull(message = "Price cannot be null")
		BigDecimal price,

		@Positive(message = "Quantity must be a positive number")
		int quantity

		
		) {

	
}
