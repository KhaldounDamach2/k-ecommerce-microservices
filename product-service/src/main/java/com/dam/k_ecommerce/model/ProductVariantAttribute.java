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
@ToString
@Table(
    name = "product_variant_attribute",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_attribute_variant",
        columnNames = {"prod_variant_id", "attribute_id"}
    )
)
public class ProductVariantAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_attribute_seq")
    @SequenceGenerator(name = "product_variant_attribute_seq", sequenceName = "product_variant_attribute_seq", allocationSize = 50)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = true)
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_variant_id", nullable = true)
    @JsonIgnore
    private ProductVariant prodVariant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id", nullable = true)
    @JsonIgnore
    private ProductAttribute attribute;
}