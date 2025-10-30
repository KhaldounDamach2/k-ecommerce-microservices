package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	 Optional<CartItem> findByProductId(Long productId);
}
