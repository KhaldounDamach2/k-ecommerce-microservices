package com.dam.k_ecommerce.order.webClient;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dam.k_ecommerce.dto.CustomerDto;

@FeignClient(name = "customer-service",url = "${application.config.customer-url}")
public interface CustomerClient {

	 //// Fetch customer details by ID
	 @GetMapping("/{customer-id}")
	Optional<CustomerDto> findCustomerById(@PathVariable("customer-id") Long customerId);

  
	
}
