package com.dam.k_ecommerce.exception;

public class ProductVariantNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ProductVariantNotFoundException(Long variantId) {
		super("Product variant with id=" + variantId + " not found");
	}

}
