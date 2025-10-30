package com.dam.k_ecommerce.order.webClient;

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

import com.dam.k_ecommerce.dto.CartRequest;
import com.dam.k_ecommerce.dto.CartResponse;
import com.dam.k_ecommerce.dto.PurchaseRequest;
import com.dam.k_ecommerce.dto.PurchaseResponse;
import com.dam.k_ecommerce.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductClient {

	@Value("${application.config.product-url}")
	private String productUrl;

	private final RestTemplate restTemplate;
	
	
	

	public PurchaseResponse purchaseVariant(PurchaseRequest requestBody){
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity <PurchaseRequest> requestEntity = new HttpEntity<>(requestBody, headers);
		ParameterizedTypeReference <PurchaseResponse> responseTyp = new ParameterizedTypeReference <PurchaseResponse>() {};
		
		ResponseEntity <PurchaseResponse> responseEntity = restTemplate.exchange(productUrl + "/purchase", HttpMethod.POST, requestEntity, responseTyp);
		if (responseEntity.getStatusCode().isError()) {
			throw new BusinessException("An Error occured while proccessing the variant purchase " + responseEntity.getStatusCode());
		}
		return responseEntity.getBody(); // Rückgabe des Antwortkörpers
	}
		
	
	
	
		public List<CartResponse> purchaseCart(List<CartRequest> requestBody) {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			
			HttpEntity <List<CartRequest>> requestEntity = new HttpEntity<>(requestBody, headers);
			ParameterizedTypeReference <List<CartResponse>> responseTyp = new ParameterizedTypeReference <List<CartResponse>>() {};
			
			
			ResponseEntity <List<CartResponse>> responseEntity = 
					restTemplate.exchange(productUrl + "/purchase-cart", HttpMethod.POST, requestEntity, responseTyp);
			
			if (responseEntity.getStatusCode().isError()) {
				throw new BusinessException("An Error occured while proccessing the Cart purchase " + responseEntity.getStatusCode());
			}
			return responseEntity.getBody(); // Rückgabe des Antwortkörpers
		
		
	}
}
