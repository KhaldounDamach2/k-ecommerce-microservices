package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dam.k_ecommerce.model.ProductVariantAttribute;

@Repository
public interface ProductVariantAttributeRepository extends JpaRepository<ProductVariantAttribute, Long> {

    @Query("SELECT pva FROM ProductVariantAttribute pva WHERE pva.attribute.id = :attributeId AND pva.prodVariant.id = :variantId")
    Optional<ProductVariantAttribute> findByVariantIdAndAttributeId(@Param("variantId") Long variantId, @Param("attributeId") Long attributeId);
}
