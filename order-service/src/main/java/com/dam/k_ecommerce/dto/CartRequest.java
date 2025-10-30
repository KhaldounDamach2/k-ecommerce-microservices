package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dam.k_ecommerce.model.PaymentMethod;

public record CartRequest(

		Long id,

		
		Long buyerId,

		List<CartItemRequest> cartItems,

		
		BigDecimal totalPrice,
		
		

		Boolean checkedOut

) {

}
