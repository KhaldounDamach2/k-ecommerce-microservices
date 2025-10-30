package com.dam.k_ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dam.k_ecommerce.cart.dto.CartItemRequest;
import com.dam.k_ecommerce.cart.dto.CartRequest;
import com.dam.k_ecommerce.service.CartItemService;
import com.dam.k_ecommerce.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	private final CartItemService cartItemService;
	


@PostMapping
public ResponseEntity<Long> createCart(@RequestBody @Valid CartItemRequest cartItemRequest, @RequestParam Long customerId) {
	final String YELLOW = "\u001B[33m";
    final String RESET = "\u001B[0m";
    log.info(YELLOW + " i am in cart Controller Received request to add to cart: {}" + RESET, cartItemRequest);
    log.info("Buyer ID: {}", customerId);

    try {
        Long cartId = cartService.addToCart(cartItemRequest, customerId);
        return ResponseEntity.ok(cartId);
    } catch (Exception e) {
        log.error("Error while adding to cart", e);
        throw e;
    }
}

	
	@DeleteMapping("/{prodVariantId}")
	public ResponseEntity<String> deleteItemByProdId(@PathVariable Long prodVariantId) {
		cartItemService.deleteItemByProdId(prodVariantId);
		return ResponseEntity.ok("Product removed from cart successfully.");
	}
	
}
