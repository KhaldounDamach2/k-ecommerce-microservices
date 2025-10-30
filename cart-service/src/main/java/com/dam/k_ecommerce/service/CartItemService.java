package com.dam.k_ecommerce.service;

import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.model.CartItem;
import com.dam.k_ecommerce.repository.CartItemRepository;
import com.dam.k_ecommerce.repository.CartRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;

	/**
	 * Löscht einen CartItem basierend auf der productId.
	 *
	 * @param productId die ID des Produkts, dessen CartItem gelöscht werden soll
	 * @throws EntityNotFoundException wenn kein CartItem mit der angegebenen
	 *                                 productId gefunden wird
	 */
	
	@Transactional
    public void deleteItemByProdId(Long productId) {
        CartItem cartItem = cartItemRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem mit productId " + productId + " nicht gefunden."));
        
     // Artikel aus dem Warenkorb entfernen
        var cart = cartItem.getCart();
        if (cart != null) {
            cart.getItems().remove(cartItem);
            cartRepository.save(cart); // Warenkorb aktualisieren
        }
        cartItemRepository.delete(cartItem);
    }
}
