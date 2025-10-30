package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;

import com.dam.k_ecommerce.model.Order;

public record OrderLineRequest(
		
		
		Long id,
		
		Long orderId,
		
		Long prodVariantId,
		
		int quantity,
		
		BigDecimal price
		
	
		
		
		
		) {

}
