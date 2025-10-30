package com.dam.k_ecommerce.cart.webClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dam.k_ecommerce.cart.dto.CartItemRequest;
import com.dam.k_ecommerce.cart.dto.CartItemResponse;
import com.dam.k_ecommerce.cart.dto.CartRequest;
import com.dam.k_ecommerce.cart.dto.CartResponse;
import com.dam.k_ecommerce.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartProductClient {

	@Value("${application.config.product-url}")
	private String productUrl;
	
	private final RestTemplate restTemplate;
	
	public CartItemResponse validateVariant(CartItemRequest requestBody){
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity <CartItemRequest> requestEntity = new HttpEntity<>(requestBody, headers);
		ParameterizedTypeReference <CartItemResponse> responseTyp = new ParameterizedTypeReference <CartItemResponse>() {};
		
		ResponseEntity <CartItemResponse> responseEntity = restTemplate.exchange(productUrl + "/validate", HttpMethod.POST, requestEntity, responseTyp);
		if (responseEntity.getStatusCode().isError()) {
			throw new BusinessException("An Error occured while proccessing the variant purchase " + responseEntity.getStatusCode());
		}
		return responseEntity.getBody(); // Rückgabe des Antwortkörpers
	}
	
	
	public CartResponse validateCart(CartRequest requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		HttpEntity <CartRequest> requestEntity = new HttpEntity<>(requestBody, headers);
		ParameterizedTypeReference <CartResponse> responseTyp = new ParameterizedTypeReference <CartResponse> () {
		};

		ResponseEntity <CartResponse> responseEntity = restTemplate.exchange(productUrl + "/validate-cart", HttpMethod.POST,
				requestEntity, responseTyp);
		if (responseEntity.getStatusCode().isError()) {
			throw new BusinessException(
					"An Error occured while proccessing the cart validation " + responseEntity.getStatusCode());
		}
		return responseEntity.getBody(); // Rückgabe des Antwortkörpers
	}
	
	
}

