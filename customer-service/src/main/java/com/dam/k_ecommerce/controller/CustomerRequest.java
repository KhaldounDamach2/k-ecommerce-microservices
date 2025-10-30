package com.dam.k_ecommerce.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Vorname darf nicht leer sein.")
    private String firstName;

    @NotBlank(message = "Nachname darf nicht leer sein.")
    private String secondName;

    @Email(message = "Ungültige E-Mail-Adresse.")
    @NotBlank(message = "E-Mail darf nicht leer sein.")
    private String email;

    @NotBlank(message = "Straße darf nicht leer sein.")
    private String street;

    @NotBlank(message = "Hausnummer darf nicht leer sein.")
    private String houseNumber;

    @NotBlank(message = "Stadt darf nicht leer sein.")
    private String city;

    @NotBlank(message = "Postleitzahl darf nicht leer sein.")
    private String zipCode;

    @NotBlank(message = "Land darf nicht leer sein.")
    private String landName;
}
