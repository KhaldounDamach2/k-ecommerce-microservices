package com.dam.k_ecommerce.product.purchase.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;

@Builder
public record CartResponse(

		Long id,
		Long buyerId,
		BigDecimal price,
		List<CartItemRequest> cartItems,
		Boolean checkedOut

) {

	// This record is currently empty, but can be extended in the future
	// to include fields related to product responses, such as product ID,
	// name, description, price, etc.

	// Example:
	// Long productId,
	// String productName,
	// String description,
	// BigDecimal price
}
