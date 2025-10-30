package com.dam.k_ecommerce.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dam.k_ecommerce.cart.dto.CartItemRequest;
import com.dam.k_ecommerce.cart.dto.CartRequest;
import com.dam.k_ecommerce.model.Cart;
import com.dam.k_ecommerce.model.CartItem;

@Component
public class CartMapper {

    public CartRequest toCartRequest(Cart cart) {
        return new CartRequest(
            cart.getId(),
            cart.getBuyerId(),
            cart.getItems().stream()
                .map(item -> new CartItemRequest(
                    
                    item.getProductId(),
                   
                    item.getPrice(),
                    item.getQuantity()
                    ))
                .collect(Collectors.toList()),
            cart.getTotalPrice(),
            cart.getCheckedOut()
        );
    }
}
