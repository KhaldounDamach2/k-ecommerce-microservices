package com.dam.k_ecommerce.product.purchase.dto;

import java.math.BigDecimal;

public record PurchaseResponse(

		Long prodVariantId,


		BigDecimal totalPrice

) {

}
