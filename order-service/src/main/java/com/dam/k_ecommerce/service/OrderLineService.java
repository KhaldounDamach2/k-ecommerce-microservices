package com.dam.k_ecommerce.service;

import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.dto.OrderLineRequest;
import com.dam.k_ecommerce.mapper.OrderLineMapper;
import com.dam.k_ecommerce.repository.OrderLineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineService {
	
	private final OrderLineRepository orderLineRepository;
	private final OrderLineMapper orderLineMapper;
	
	public Long saveOrderLine(OrderLineRequest request) {
		
		var order = orderLineMapper.toOrderLine(request);
	
		return orderLineRepository.save(order).getId();
	}

}
