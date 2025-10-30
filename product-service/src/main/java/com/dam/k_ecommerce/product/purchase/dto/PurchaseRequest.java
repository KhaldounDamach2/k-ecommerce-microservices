package com.dam.k_ecommerce.product.purchase.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PurchaseRequest(
		
		
		@NotNull(message = "Product ID cannot be null")
		Long prodVariantId,


		@Positive(message = "Quantity must be a positive number")
		int quantity
		
		) {

	public PurchaseRequest {
        if (prodVariantId == null || prodVariantId <= 0) {
            throw new IllegalArgumentException("variantId must be a positive number");
        }
        if (quantity <= 0) {
            quantity = 1; // Standardwert setzen
        }
    }
}
