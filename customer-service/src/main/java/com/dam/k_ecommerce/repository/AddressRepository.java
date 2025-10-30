package com.dam.k_ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByLandId(Long landId);

	List<Address> findByLandLandName(String landName);

}
