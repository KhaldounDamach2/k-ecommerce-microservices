package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dam.k_ecommerce.model.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderDto(
		
		Long orderId,

		

		@Positive(message = "Amount must be positive")
		BigDecimal amount,

		@NotNull(message = "Customer ID cannot be null")
		Long customerId,
		
		

		@NotNull(message = "Payment method cannot be null")
		PaymentMethod paymentMethod,

		@NotEmpty(message = "Products cannot be empty")
		PurchaseRequest prodVariant
		
		) {

}
