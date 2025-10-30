package com.dam.k_ecommerce.dto;

import java.math.BigDecimal;

public record CartItemResponse(

		Long prodVariantId,

		int quantity,

		BigDecimal price

) {

}
