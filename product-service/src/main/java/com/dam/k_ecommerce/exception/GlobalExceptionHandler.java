package com.dam.k_ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
    
    @ExceptionHandler(ProductVariantNotFoundException.class)
    public ResponseEntity<String> handleProductVariantNotFoundException(ProductVariantNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAnyUnhandled(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 500,
                "error", "Unexpected error: " + ex.getMessage()
            )
        );
    }
    
    
    
    
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStockException(InsufficientStockException ex) {       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
