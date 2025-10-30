package com.dam.k_ecommerce.mapper;

import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.dto.OrderLineRequest;
import com.dam.k_ecommerce.model.Order;
import com.dam.k_ecommerce.model.OrderLine;

@Service
public class OrderLineMapper {
	
	public OrderLine toOrderLine(OrderLineRequest request) {
		// Implement the mapping logic here
		return OrderLine.builder()
				.id(request.id())
				.prodVariantId(request.prodVariantId())
				.quantity(request.quantity())
				.price(request.price())
				.order(
						Order.builder()
						.id(request.orderId())
                        .build()
                        
                        ).build();
				
	}

	public OrderLineRequest toOrderLineRequest(OrderLine orderLine) {
		// Implement the reverse mapping logic here
		return null;
	}

}
