package com.dam.k_ecommerce.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByCatName(String catName);

}
