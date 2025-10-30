package com.dam.k_ecommerce.product.purchase.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.exception.InsufficientStockException;
import com.dam.k_ecommerce.exception.ProductVariantNotFoundException;
import com.dam.k_ecommerce.model.ProductVariant;
import com.dam.k_ecommerce.product.purchase.dto.CartItemRequest;
import com.dam.k_ecommerce.product.purchase.dto.CartItemResponse;
import com.dam.k_ecommerce.product.purchase.dto.CartRequest;

import com.dam.k_ecommerce.product.purchase.dto.CartResponse;
import com.dam.k_ecommerce.repository.ProductVariantRepository;
import com.dam.k_ecommerce.webClients.CartItemDeleteClient;
import com.dam.k_ecommerce.webClients.OrderClient;

import com.dam.k_ecommerce.product.purchase.dto.PurchaseRequest;
import com.dam.k_ecommerce.product.purchase.dto.PurchaseResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {

	private final CartItemDeleteClient cartItemClient;

	private final ProductVariantRepository productVariantRepository;
	private final OrderClient orderClient;
	
	

	@Transactional
	public PurchaseResponse purchaseVariant(PurchaseRequest chosen) {
		Long variantId = chosen.prodVariantId();
		int quantity = chosen.quantity();

		ProductVariant variant = productVariantRepository.findById(variantId)
				.orElseThrow(() -> new ProductVariantNotFoundException(variantId));

		if (variant.getStock() < quantity) {
			throw new InsufficientStockException(variantId);
		}

		variant.setStock(variant.getStock() - quantity);
		productVariantRepository.save(variant);

		// checking the stock in CartItems after purchase
		if (variant.getStock() < 10) {
			this.cartItemClient.deleteProdFromCart(variantId);
		}

		BigDecimal totalPrice = variant.getPrice().multiply(BigDecimal.valueOf(quantity));

		log.info("Purchased {} of variant id={}, remaining stock={}", quantity, variantId, variant.getStock());

		return new PurchaseResponse(variant.getId(), totalPrice);
	}
	
	
	

	@Transactional
	public CartItemResponse validateProduct(CartItemRequest request) {
		Long variantId = request.prodVariantId();
		int quantity = request.quantity();

		ProductVariant variant = productVariantRepository.findById(variantId)
				.orElseThrow(() -> new ProductVariantNotFoundException(variantId));

		if (variant.getStock() < quantity) {
			throw new InsufficientStockException(variantId);
		}

		// BigDecimal totalPrice =
		// variant.getPrice().multiply(BigDecimal.valueOf(quantity));
		return new CartItemResponse(variant.getId(), request.quantity(), variant.getPrice());
	}
	
	
	
	

	@Transactional
	public CartResponse processCart(CartRequest cartRequest) {
		// Validierung des Warenkorbs
		if (cartRequest.cartItems() == null || cartRequest.cartItems().isEmpty()) {
			throw new IllegalArgumentException("Cart items cannot be null or empty");
		}

		var cartItems = cartRequest.cartItems();
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (int i = 0; i < cartItems.size(); i++) {
			CartItemRequest itemRequest = cartItems.get(i);
			CartItemResponse variant = this.validateProduct(itemRequest);

			if (variant == null) {
				this.cartItemClient.deleteProdFromCart(itemRequest.prodVariantId());
				cartItems.remove(i);
				i--; // Index anpassen, da die Liste verkleinert wurde
				continue;
			}

			// Berechnung des Gesamtpreises
			totalPrice = totalPrice.add(variant.price().multiply(BigDecimal.valueOf(itemRequest.quantity())));
		}

		// Wenn `checkedOut` true ist, den Kaufprozess starten
		if (Boolean.TRUE.equals(cartRequest.checkedOut())) {
			return orderClient.purchaseOrderFromCart(
					new CartRequest(cartRequest.id(), cartRequest.buyerId(), cartItems, totalPrice, true));
		}

		// Rückgabe der validierten CartResponse
		return new CartResponse(cartRequest.id(), cartRequest.buyerId(), totalPrice, cartItems, false);
	}

}
