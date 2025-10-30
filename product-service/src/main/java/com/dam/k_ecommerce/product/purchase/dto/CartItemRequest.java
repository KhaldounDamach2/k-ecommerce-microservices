package com.dam.k_ecommerce.product.purchase.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequest(

		
		
		Long prodVariantId,

		
		BigDecimal price,

		
		int quantity

) {

}
