package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.dto.CustomerDto;
import com.dam.k_ecommerce.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findCartResponseById(Long id);

}
