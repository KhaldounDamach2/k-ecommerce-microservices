package com.dam.k_ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.k_ecommerce.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
    List<Customer> findByAddressId(Long addressId);
    List<Customer> findByAddressLandId(Long landId);
    List<Customer> findByAddressLandLandName(String landName);
	boolean existsByEmail(String email);
   
    
}
