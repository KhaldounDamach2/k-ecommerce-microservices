package com.dam.k_ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String msg;
}
