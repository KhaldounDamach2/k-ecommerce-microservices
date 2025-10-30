package com.dam.k_ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

	private Long id;

	@NotBlank(message = "Name cannot be blank")
	private String clientName;

	@NotBlank(message = "email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "phone cannot be blank")
	private String phone;

}
