package com.dam.k_ecommerce.exceptions;


public class InvalidCustomerDataException extends RuntimeException {

	public InvalidCustomerDataException(String message) {
		super(message);
	}

	public InvalidCustomerDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
