
package com.dam.k_ecommerce.cart.webClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dam.k_ecommerce.controller.CartController;
import com.dam.k_ecommerce.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartCustomerClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.customer-url}")
    private String customerServiceUrl;



public Long findById(Long customerId) {
    String url = customerServiceUrl + "/cart/" + customerId;
    final String YELLOW = "\u001B[33m";
    final String RESET = "\u001B[0m";
    log.info(YELLOW + "Requesting URL: {}" + RESET, url);

    try {
        log.info(YELLOW + "Calling CustomerService: {}" + RESET, url);

        Long result = restTemplate.getForObject(url, Long.class);

        if (result == null) {
            throw new BusinessException("Kunde mit ID " + customerId + " nicht gefunden.");
        }

        log.info(YELLOW + "Customer ID returned: {}" + RESET, result);
        return result;

    } catch (Exception e) {
        log.error("Fehler beim Abrufen des Kunden mit ID: {}", customerId, e);
        throw new BusinessException("Fehler beim Abrufen des Kunden mit ID: " + customerId, e);
    }
}


}