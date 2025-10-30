package com.dam.k_ecommerce.webClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemDeleteClient {
	
	@Value("${application.config.cart-url}")
	private String cartUrl;
	
	private final RestTemplate restTemplate;
	
	public void deleteProdFromCart(Long prodVariantId) {
		String url = cartUrl + "/" + prodVariantId;
		restTemplate.delete(url);
	}

}
