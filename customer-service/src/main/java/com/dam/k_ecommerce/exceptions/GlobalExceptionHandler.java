
package com.dam.k_ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		// Rückgabe einer benutzerdefinierten Fehlermeldung mit dem HTTP-Status 400 (Bad
		// Request)
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	
	 @ExceptionHandler(InvalidCustomerDataException.class)
	    public ResponseEntity<String> handleInvalidCustomerDataException(InvalidCustomerDataException exc) {
	        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	
}



