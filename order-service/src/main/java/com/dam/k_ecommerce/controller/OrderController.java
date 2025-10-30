package com.dam.k_ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dam.k_ecommerce.dto.CartRequest;
import com.dam.k_ecommerce.dto.CartResponse;
import com.dam.k_ecommerce.dto.OrderDto;
import com.dam.k_ecommerce.dto.OrderRequest;
import com.dam.k_ecommerce.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
		Long orderId = orderService.createOrder(orderRequest);
		return ResponseEntity.ok(orderId);
	}
	
	
	@PostMapping("/create-from-cart")
	public ResponseEntity<CartResponse> createOrderfromCart(@RequestBody @Valid CartRequest cartRequest) {
		CartResponse cartResponse = orderService.createOrderfromCart(cartRequest);
		return ResponseEntity.ok(cartResponse);
	}
	
}
