package com.dam.k_ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@ToString(exclude = {"attributes"})
@Table(name = "product_attribute")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_attribute_seq")
    @SequenceGenerator(name = "product_attribute_seq", sequenceName = "product_attribute_seq", allocationSize = 50)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = true)
    private String attrName;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductVariantAttribute> attributes = new ArrayList<>();
    
	public void addAttribute(ProductVariantAttribute attribute) {
		if (attribute != null && attribute.getValue() != null && !attribute.getValue().isBlank()) {
		attributes.add(attribute);
		attribute.setAttribute(this);
	}
		}

	public void removeAttribute(ProductVariantAttribute attribute) {
		attributes.remove(attribute);
		attribute.setAttribute(null);
	}
}

