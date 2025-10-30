package com.dam.k_ecommerce.cart.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dam.k_ecommerce.model.PaymentMethod;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartRequest(

		Long id,
		
		@NotNull(message = "Customer ID cannot be null")
		Long buyerId,
		
		@NotEmpty(message = "Cart items cannot be empty")
		List<CartItemRequest> cartItems,
		
				
		@Positive(message = "Order amount should be positive")
		BigDecimal totalPrice,
		
		
		Boolean checkedOut
		

) {

	

}
