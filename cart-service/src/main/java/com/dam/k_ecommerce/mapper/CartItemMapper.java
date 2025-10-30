package com.dam.k_ecommerce.mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.cart.dto.CartItemRequest;
import com.dam.k_ecommerce.model.CartItem;

@Component
public class CartItemMapper {

	public CartItemRequest toCartItemRequest(CartItem cartItem) {
		return new CartItemRequest(
			
				
				cartItem.getProductId(),				
				cartItem.getPrice(),
				cartItem.getQuantity().intValue()
				);
	}
	
	public CartItem toCartItem(CartItemRequest cartItemRequest) {
		return new CartItem(
			null,
			null,
				
				cartItemRequest.prodVariantId(),
				cartItemRequest.quantity(),
				cartItemRequest.price()
				
				);
	}
}
