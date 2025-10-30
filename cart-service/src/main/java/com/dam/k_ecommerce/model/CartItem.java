package com.dam.k_ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Product ID cannot be null")
    private Long productId; // ID des Produkts

    @NotNull
    private Integer quantity;// Menge des Produkts
    
    @NotNull
    private BigDecimal price; // Preis des Produkts
}
