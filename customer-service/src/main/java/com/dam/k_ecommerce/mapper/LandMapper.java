package com.dam.k_ecommerce.mapper;

import org.springframework.stereotype.Component;

import com.dam.k_ecommerce.dto.LandDto;
import com.dam.k_ecommerce.model.Land;

@Component
public class LandMapper {
	
	
	  public Land toEntity(LandDto landDto) {
		  if (landDto == null) {
			  return null; // Handle null cases appropriately
		  }
			return Land.builder().landName(landDto.getLandName()).build();
	  }
	  
		public LandDto toDto(Land land) {
			if (land == null) {
				return null; // Handle null cases appropriately
			}
			return LandDto.builder().landName(land.getLandName()).build();
		}
}
