package com.dam.k_ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.dto.CartItemRequest;
import com.dam.k_ecommerce.dto.CartRequest;
import com.dam.k_ecommerce.dto.CartResponse;
import com.dam.k_ecommerce.dto.OrderDto;
import com.dam.k_ecommerce.dto.OrderRequest;
import com.dam.k_ecommerce.dto.PurchaseRequest;
import com.dam.k_ecommerce.model.Order;
import com.dam.k_ecommerce.model.OrderLine;

@Component
public class OrderMapper {

	
	 public Order toOrder(OrderRequest request) {
	
		 Order order = new Order();
				 
		return Order.builder()
				.id(request.orderId())
				
				.customerId(request.customerId())
				.totalAmount(request.amount())
				.paymentMethod(request.paymentMethod())								
				.build();
		 
	 }
	 
		
		

		public Order toOrder(CartRequest cartRequest) {
	        // Konvertiere CartItems zu OrderLines
	        List<OrderLine> orderLines = cartRequest.cartItems().stream()
	                .map(this::toOrderLine)
	                .collect(Collectors.toList());

	        // Erstelle die Order-Entität
	        return Order.builder()
	                .customerId(cartRequest.buyerId())
	                .totalAmount(cartRequest.totalPrice())
	                .orderLines(orderLines)
	                .build();
	    }

	    private OrderLine toOrderLine(CartItemRequest cartItemRequest) {
	        return OrderLine.builder()
	                .prodVariantId(cartItemRequest.prodVariantId())
	                .quantity(cartItemRequest.quantity())
	                .price(cartItemRequest.price())
	                .build();
	    }
}

