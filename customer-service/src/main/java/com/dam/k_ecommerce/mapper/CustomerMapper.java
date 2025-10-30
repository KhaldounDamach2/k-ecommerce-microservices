package com.dam.k_ecommerce.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Component;

import com.dam.k_ecommerce.dto.AddressDto;
import com.dam.k_ecommerce.dto.CustomerDto;
import com.dam.k_ecommerce.dto.LandDto;
import com.dam.k_ecommerce.model.Address;
import com.dam.k_ecommerce.model.Customer;
import com.dam.k_ecommerce.model.Land;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    
	private final AddressMapper addressMapper; // For handling Address conversions

	/**
	 * Converts DTO to Entity for CREATE operations
	 */
	public Customer toEntity(CustomerDto dto) {
		if (dto == null ) {
			return null; // Handle null cases appropriately
		}
		return Customer.builder().firstName(dto.getFirstName()).secondName(dto.getSecondName())
				.email(dto.getEmail().toLowerCase().trim()) // Normalize email				
				.build();
		
	}

	
    
	/**
	 * Converts DTO to Entity for UPDATE operations, including Address and Land.
	 */
	public Customer toEntity(CustomerDto dto, AddressDto addressDto, LandDto landDto,  Customer existingEntity) {
	    if (dto == null || existingEntity == null) {
	        return null; // Handle null cases appropriately
	    }

	    // Update customer information
	    if (dto.getFirstName() != null) {
	        existingEntity.setFirstName(dto.getFirstName());
	    }
	    if (dto.getSecondName() != null) {
	        existingEntity.setSecondName(dto.getSecondName());
	    }
	    if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(existingEntity.getEmail())) {
	        existingEntity.setEmail(dto.getEmail().toLowerCase().trim());
	    }

		if (addressDto != null) {
			// Convert AddressDto to Address entity
			Address addressEntity = addressMapper.toEntity(addressDto, landDto);
			if (addressEntity != null) {
				existingEntity.setAddress(addressEntity); // Set the new address
			}
		}
	       
	    return existingEntity;
	}
	
	/**
	 * Converts Entity to DTO for READ operations
	 */
	public CustomerDto toDto(Customer entity) {
		if (entity == null) {
			return null; // Handle null cases appropriately
		}

		CustomerDto dto = CustomerDto.builder().firstName(entity.getFirstName()).secondName(entity.getSecondName())
				.email(entity.getEmail()).build();

			if (entity.getAddress() != null) {
				AddressDto addressDto = addressMapper.toDto(entity.getAddress());
				dto.setAddressDto(addressDto);
			} else {
				dto.setAddressDto(null); // Ensure address is set to null if not present
			}

		return dto;
	}
    
}
    
