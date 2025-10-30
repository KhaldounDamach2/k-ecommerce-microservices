package com.dam.k_ecommerce.product.purchase.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartRequest(

		Long id,

		 
		Long buyerId,

		List<CartItemRequest> cartItems,

		
		BigDecimal totalPrice,

		Boolean checkedOut

) {

}
