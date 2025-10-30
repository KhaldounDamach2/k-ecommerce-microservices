
package com.dam.k_ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"prodVariant"})
@Table(
    name = "product_variant_photo",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_product_variant_photo",
        columnNames = {"prod_variant_id", "url"}
    )
)
public class ProductVariantPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_photo_seq")
    @SequenceGenerator(name = "product_variant_photo_seq", sequenceName = "product_variant_photo_seq", allocationSize = 50)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "URL cannot be blank")
    @Column(nullable = false)
    private String url;

    @Column(nullable = true)
    private String altText;

    @NotNull(message = "Position cannot be null")
    @Column(nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_variant_id", nullable = false)
    @JsonIgnore
    private ProductVariant prodVariant;

    
}
