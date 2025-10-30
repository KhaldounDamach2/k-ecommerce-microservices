package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;



public record CartResponse(
		
		Long id,
		Long buyerId,
		BigDecimal price,
		List<CartItemRequest> cartItems,
	    Boolean checkedOut

		
		) {

}
