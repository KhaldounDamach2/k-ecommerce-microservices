package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.Category;
import com.dam.k_ecommerce.model.ProductAttribute;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
	
	public Optional<ProductAttribute> findByAttrName(String attrName);

}
