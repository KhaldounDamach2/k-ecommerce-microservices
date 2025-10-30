package com.dam.k_ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.cart.dto.CartItemRequest;
import com.dam.k_ecommerce.cart.dto.CartRequest;
import com.dam.k_ecommerce.cart.dto.CartResponse;
import com.dam.k_ecommerce.cart.webClient.CartCustomerClient;
import com.dam.k_ecommerce.cart.webClient.CartOrderClient;
import com.dam.k_ecommerce.cart.webClient.CartProductClient;
import com.dam.k_ecommerce.exception.BusinessException;
import com.dam.k_ecommerce.model.Cart;
import com.dam.k_ecommerce.model.CartItem;
import com.dam.k_ecommerce.repository.CartItemRepository;
import com.dam.k_ecommerce.repository.CartRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;

	private final CartCustomerClient cartCustomerClient;
	private final CartProductClient cartProductClient;
	private final CartOrderClient cartOrderClient;


public Long addToCart(@Valid CartItemRequest itemRequest, Long customerId) {

	final String YELLOW = "\u001B[33m";
    final String RESET = "\u001B[0m";
	 // Überprüfen, ob der Kunde existiert
	log.info(YELLOW + " i am i cart service and cecking if a coustomer is foung with Id: {}" + RESET, customerId);
    Long customer_Id = this.cartCustomerClient.findById(customerId);
    if (customer_Id == null) {
        throw new BusinessException("Cannot add to cart:: No Customer exist with ID: " + customerId);
    }

    // Validieren der Produktvariante
    log.info(YELLOW + "i am in cart service and Validating product variant with ID: {}" + RESET, itemRequest.prodVariantId());
    var productVariant = this.cartProductClient.validateVariant(itemRequest);
    if (productVariant == null) {
        throw new BusinessException(
        		YELLOW + "Cannot add to cart:: No Product Variant exist with ID: " + itemRequest.prodVariantId() + RESET);
    }

    // Überprüfen, ob ein Warenkorb für den Kunden existiert
    log.info(YELLOW +"Checking if a cart exists for customer ID: {}" + RESET, customerId);
    var existingCart = cartRepository.findCartByCustomerId(customerId).orElse(null);

    // Erstellen eines neuen CartItem
    log.info(YELLOW +"Creating a new CartItem for product variant ID: {}" + RESET, itemRequest.prodVariantId());
    CartItem item = new CartItem();
    item.setProductId(itemRequest.prodVariantId());
    item.setPrice(itemRequest.price());
    item.setQuantity(itemRequest.quantity());

    BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

    // Wenn ein Warenkorb existiert, Artikel hinzufügen oder aktualisieren
    log.info(YELLOW +"Processing cart for customer ID: {}" + RESET, customerId);
    if (existingCart != null) {
        // Überprüfen, ob der Artikel bereits im Warenkorb existiert
        CartItem existingItem = existingCart.getItems().stream()
            .filter(cartItem -> cartItem.getProductId().equals(item.getProductId()))
            .findFirst()
            .orElse(null);

        // Wenn der Artikel bereits existiert, aktualisieren wir die Menge
        log.info(YELLOW + "Checking if item already exists in the cart for product variant ID: {}" + RESET, item.getProductId());
        if (existingItem != null) {
            // Menge aktualisieren, wenn der Artikel bereits existiert
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // Artikel hinzufügen, wenn er nicht existiert
        	            log.info(YELLOW + "Adding new item to existing cart for product variant ID: {}" + RESET, item.getProductId());
            item.setCart(existingCart);
            cartItemRepository.save(item);
            existingCart.getItems().add(item);
        }

        // Gesamtpreis neu berechnen
        log.info(YELLOW + "Calculating total price for the cart with ID: {}" + RESET, existingCart.getId());
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : existingCart.getItems()) {
            BigDecimal itemTotalPrice = cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            cartTotalPrice = cartTotalPrice.add(itemTotalPrice);
        }

        existingCart.setTotalPrice(cartTotalPrice);
        cartRepository.save(existingCart);
        return existingCart.getId();
    } else {
        // Neuen Warenkorb erstellen
    	// Neuen Warenkorb erstellen
    	log.info(YELLOW + "Creating a new cart for customer ID: {}" + RESET, customerId);
    	var cart = new Cart();

    	cart.setBuyerId(customerId);
    	cart.setItems(new ArrayList<>());
    	cart.setTotalPrice(itemTotal);
    	cart.setCheckedOut(false);

    	// Speichern des neuen Warenkorbs
    	cartRepository.save(cart);

    	// Setzen der Beziehung zwischen CartItem und Cart
    	item.setCart(cart);

    	// Speichern des CartItems
    	cartItemRepository.save(item);

    	// Hinzufügen des CartItems zum Warenkorb
    	cart.getItems().add(item);

    	// Rückgabe der Cart-ID
    	return cart.getId();
    }
}

	public Long checkOutCart(@Valid CartRequest request) {

		CartResponse cartItems = cartProductClient.validateCart(request);
		
		if (cartItems == null) {
			throw new BusinessException("Cannot checkout cart:: Cart is empty or invalid.");
		}

		return null;
	}

}
