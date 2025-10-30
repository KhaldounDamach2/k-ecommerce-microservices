package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dam.k_ecommerce.model.Category;
import com.dam.k_ecommerce.model.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @Query("SELECT v FROM ProductVariant v WHERE v.sku = :sku AND v.product.id = :productId")
    Optional<ProductVariant> findBySkuAndProductId(@Param("sku") String sku, @Param("productId") Long productId);
}
