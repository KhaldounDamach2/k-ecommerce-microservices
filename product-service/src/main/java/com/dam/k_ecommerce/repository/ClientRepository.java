package com.dam.k_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dam.k_ecommerce.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("SELECT c FROM Client c WHERE c.clientName = :clientName AND c.email = :email")
    Optional<Client> findByNameAndEmail(@Param("clientName") String clientName, @Param("email") String email);

    Optional<Client> findByClientName(String clientName);
}
