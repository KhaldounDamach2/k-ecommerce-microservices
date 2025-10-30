package com.dam.k_ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
@ToString(exclude = {"product", "photos", "varAttributes"})
@Table(
  name = "product_variant",
  uniqueConstraints = @UniqueConstraint(
    name = "uq_product_variant",
    columnNames = {"product_id", "sku"}
  )
)
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_seq")
    @SequenceGenerator(name = "product_variant_seq", sequenceName = "product_variant_seq", allocationSize = 50)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "SKU cannot be blank")
	@Column(nullable = false)
	private String sku;
    
    @NotNull(message = "Price cannot be null")
	@Column(nullable = false)
	private BigDecimal price;
    
    @NotNull(message = "Stock date cannot be null")
	@Column(nullable = false)
	private Integer stock;
    
    @PastOrPresent(message = "Manufacture date cannot be in the future")
    @Column(nullable = true)
    private LocalDate manufDate;
    
    @Column(nullable = true)
    private String model;

    @NotNull(message = "Product cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "prodVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductVariantPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "prodVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductVariantAttribute> varAttributes = new ArrayList<>();

    public void addPhoto(ProductVariantPhoto photo) {
        photos.add(photo);
        photo.setProdVariant(this);
    }

    public void removePhoto(ProductVariantPhoto photo) {
    	photos.remove(photo);
    	photo.setProdVariant(null);
    }

    public void addAttribute(ProductVariantAttribute varAttribute) {
        varAttributes.add(varAttribute);
        varAttribute.setProdVariant(this);
    }

    public void removeAttribute(ProductVariantAttribute varAttribute) {
    	varAttributes.remove(varAttribute);
    	varAttribute.setProdVariant(null);
    }
}
