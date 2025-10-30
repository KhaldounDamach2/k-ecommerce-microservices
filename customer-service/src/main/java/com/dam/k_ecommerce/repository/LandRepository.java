package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.Land;

public interface LandRepository extends JpaRepository<Land, Long> {

	Optional<Land> findByLandName(String landName);
}
