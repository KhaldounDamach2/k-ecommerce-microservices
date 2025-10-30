package com.dam.k_ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dam.k_ecommerce.dto.ProductDto;
import com.dam.k_ecommerce.product.purchase.dto.CartItemRequest;
import com.dam.k_ecommerce.product.purchase.dto.CartItemResponse;
import com.dam.k_ecommerce.product.purchase.dto.CartRequest;
import com.dam.k_ecommerce.product.purchase.dto.CartResponse;
import com.dam.k_ecommerce.product.purchase.dto.PurchaseRequest;
import com.dam.k_ecommerce.product.purchase.dto.PurchaseResponse;
import com.dam.k_ecommerce.product.purchase.service.PurchaseService;
import com.dam.k_ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	    private final PurchaseService purchaseService;

    @GetMapping("/variant-quantity/{variantId}")
    public ResponseEntity<Integer> getVariantQuantity(@PathVariable Long variantId) {
        int quantity = productService.getVariantQuantity(variantId);
        return ResponseEntity.ok(quantity);
    }
    
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchaseVariant(
            @RequestBody PurchaseRequest request
    ) {
        return ResponseEntity.ok(purchaseService.purchaseVariant(request));
    }
    
    
    @PostMapping("/validate")
    public ResponseEntity<CartItemResponse> validateProduct(@RequestBody CartItemRequest request){
    	CartItemResponse cartItemResponse = purchaseService.validateProduct(request);
    	return ResponseEntity.ok(cartItemResponse);
    	
    }
    
    @PostMapping("/validate-cart")
	public ResponseEntity <CartResponse> validateCart(@RequestBody CartRequest request) {
		CartResponse cartResponse = purchaseService.processCart(request);
		return ResponseEntity.ok(cartResponse);
	}
}
