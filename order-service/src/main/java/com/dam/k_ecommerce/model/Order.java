package com.dam.k_ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer_order")
public class Order {

	@Id
	@GeneratedValue
	private Long id;

	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	private Long customerId; // ID des Kunden, der die Bestellung aufgegeben hat

	
	@OneToMany(mappedBy = "order")
	private List<OrderLine> orderLines;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate; // Zeitpunkt der Bestellung

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedDate; // Zeitpunkt der letzten Aktualisierung der Bestellung
}
