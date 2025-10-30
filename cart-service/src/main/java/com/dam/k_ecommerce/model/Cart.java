package com.dam.k_ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long buyerId; // ID des Käufers
    
    @Builder.Default
    BigDecimal totalPrice = BigDecimal.ZERO; // Gesamtpreis des Warenkorbs

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Builder.Default
    @NotNull
    private Boolean checkedOut = false; // Status des Warenkorbs
}
