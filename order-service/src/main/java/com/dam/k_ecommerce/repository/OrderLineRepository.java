package com.dam.k_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
