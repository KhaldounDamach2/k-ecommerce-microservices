package com.dam.k_ecommerce.mapper;

import org.springframework.stereotype.Component;

import com.dam.k_ecommerce.dto.AddressDto;
import com.dam.k_ecommerce.dto.CustomerDto;
import com.dam.k_ecommerce.dto.LandDto;
import com.dam.k_ecommerce.model.Address;
import com.dam.k_ecommerce.model.Land;

@Component
public class AddressMapper {

	public Address toEntity(AddressDto addressDto, LandDto landDto) {
		if (addressDto == null) {
			return null; // Handle null cases appropriately
		}
		
		return Address.builder().street(addressDto.getStreet())
				.houseNumber(addressDto.getHouseNumber()).city(addressDto.getCity()).zipCode(addressDto.getZipCode())
				.land(Land.builder().landName(landDto.getLandName()).build()).build();
	}
	
	public AddressDto toDto(Address address) {
		if (address == null) {
			return null; // Handle null cases appropriately
		}

		return AddressDto.builder()
			    .street(address.getStreet())
			    .houseNumber(address.getHouseNumber())
			    .city(address.getCity())
			    .zipCode(address.getZipCode())
			    .land(LandDto.builder()
			        .landName(address.getLand().getLandName())
			        .build())
			    
                .build();
	}

}
