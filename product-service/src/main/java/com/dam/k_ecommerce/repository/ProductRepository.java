package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.Category;
import com.dam.k_ecommerce.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProdNameAndClientId(String prodName, Long clientId);

    // Custom query methods can be defined here if needed
    // For example, to find products by category:
    // List<Product> findByCategoryName(String categoryName);

}

