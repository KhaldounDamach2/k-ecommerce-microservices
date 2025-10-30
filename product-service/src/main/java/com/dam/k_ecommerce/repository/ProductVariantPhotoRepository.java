package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dam.k_ecommerce.model.ProductVariantPhoto;

@Repository
public interface ProductVariantPhotoRepository extends JpaRepository<ProductVariantPhoto, Long> {

    @Query("SELECT p FROM ProductVariantPhoto p WHERE p.prodVariant.id = :variantId AND p.url = :url")
    Optional<ProductVariantPhoto> findByVariantIdAndUrl(@Param("variantId") Long variantId, @Param("url") String url);
}
