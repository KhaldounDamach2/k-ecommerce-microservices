package com.dam.k_ecommerce.webClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dam.k_ecommerce.product.purchase.dto.CartRequest;
import com.dam.k_ecommerce.product.purchase.dto.CartResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderClient {

	@Value("${application.config.order-url}")
	private String orderUrl;
	
	
	
private final RestTemplate restTemplate;
	
	public CartResponse purchaseOrderFromCart(CartRequest requestBody){
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity <CartRequest> requestEntity = new HttpEntity<>(requestBody, headers);
		ParameterizedTypeReference <CartResponse> responseTyp = new ParameterizedTypeReference <CartResponse>() {};
		
		ResponseEntity <CartResponse> responseEntity = restTemplate.exchange(orderUrl + "/create-from-cart", HttpMethod.POST, requestEntity, responseTyp);
		if (responseEntity.getStatusCode().isError()) {
			throw new RuntimeException("An Error occurred while processing the Cart purchase " + responseEntity.getStatusCode());
		}
		return responseEntity.getBody(); // Rückgabe des Antwortkörpers
	}
}
