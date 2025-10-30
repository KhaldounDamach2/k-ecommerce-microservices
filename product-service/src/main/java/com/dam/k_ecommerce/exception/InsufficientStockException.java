package com.dam.k_ecommerce.exception;


public class InsufficientStockException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
    public InsufficientStockException(Long variantId) {
        super("Insufficient stock for variant id=" + variantId);
       
    }
   
}
