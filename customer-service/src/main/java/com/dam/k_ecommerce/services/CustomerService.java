package com.dam.k_ecommerce.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.controller.CustomerResponse;
import com.dam.k_ecommerce.dto.AddressDto;
import com.dam.k_ecommerce.dto.CustomerDto;
import com.dam.k_ecommerce.dto.LandDto;
import com.dam.k_ecommerce.exceptions.InvalidCustomerDataException;
import com.dam.k_ecommerce.mapper.AddressMapper;
import com.dam.k_ecommerce.mapper.CustomerMapper;
import com.dam.k_ecommerce.mapper.LandMapper;
import com.dam.k_ecommerce.model.Address;
import com.dam.k_ecommerce.model.Customer;
import com.dam.k_ecommerce.model.Land;
import com.dam.k_ecommerce.repository.AddressRepository;
import com.dam.k_ecommerce.repository.CustomerRepository;
import com.dam.k_ecommerce.repository.LandRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerService {

	AddressMapper addressMapper;
	CustomerMapper customerMapper;
	LandMapper landMapper;

	CustomerRepository customerRepository;
	AddressRepository addressRepository;
	LandRepository landRepository;

	@Transactional
	public void createCustomer(String firstName, String secondName, String email, String street, String houseNumber,
			String city, String zipCode, String landName) {

		// Validierung der Eingaben
		if (firstName == null || firstName.isBlank()) {
			throw new InvalidCustomerDataException("Vorname darf nicht leer sein.");
		}
		
		if(email == null || email.isBlank()) {
            throw new InvalidCustomerDataException("E-Mail darf nicht leer sein.");
        }
		
		if (!isValidEmail(email)) {
			throw new InvalidCustomerDataException("Ungültige E-Mail-Adresse.");
		}
		
		if (secondName == null || secondName.isBlank()) {
			throw new InvalidCustomerDataException("Nachname darf nicht leer sein.");
		}
		
		if (street == null || street.isBlank()) {
			throw new InvalidCustomerDataException("Straße darf nicht leer sein.");
		}
		
		if (houseNumber == null || houseNumber.isBlank()) {
			throw new InvalidCustomerDataException("Hausnummer darf nicht leer sein.");
		}
		
		if (city == null || city.isBlank()) {
			throw new InvalidCustomerDataException("Stadt darf nicht leer sein.");
		}
		
		if (zipCode == null || zipCode.isBlank()) {
			throw new InvalidCustomerDataException("Postleitzahl darf nicht leer sein.");
		}
		
		if (landName == null || landName.isBlank()) {
			throw new InvalidCustomerDataException("Land darf nicht leer sein.");
		}
		
		
		
		// Überprüfen, ob der Kunde bereits existiert
		if (customerRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("Ein Kunde mit dieser E-Mail-Addresse existiert bereits.");
		}
		
		// Land suchen oder erstellen
		Land landEntity = landRepository.findByLandName(landName).orElseGet(() -> {
			Land newLand = new Land();
			newLand.setLandName(landName);
			return landRepository.save(newLand);
		});

		// Adresse erstellen und speichern
		AddressDto addressDto = new AddressDto();
		addressDto.setStreet(street);
		addressDto.setHouseNumber(houseNumber);
		addressDto.setCity(city);
		addressDto.setZipCode(zipCode);
		Address addressEntity = addressMapper.toEntity(addressDto, landMapper.toDto(landEntity));
		addressEntity.setLand(landEntity);
		addressRepository.save(addressEntity);

		// Kunde erstellen und speichern
		CustomerDto newCustomer = new CustomerDto();
		newCustomer.setFirstName(firstName);
		newCustomer.setSecondName(secondName);
		newCustomer.setEmail(email);

		Customer customerEntity = customerMapper.toEntity(newCustomer);
		customerEntity.setAddress(addressEntity);
		customerRepository.save(customerEntity);

	}
	
	
	//update customer information

	@Transactional
	public boolean updateCustomer(Long id, String firstName, String secondName, String email, String street,
	                               String houseNumber, String city, String zipCode, String landName) {
	    Optional<Customer> customerOptional = customerRepository.findById(id);
	    if (customerOptional.isPresent()) {
	        if (firstName == null || firstName.isBlank()) {
	            throw new InvalidCustomerDataException("Vorname darf nicht leer sein.");
	        }
	        if (email == null || email.isBlank() || !isValidEmail(email)) {
	            throw new InvalidCustomerDataException("Ungültige E-Mail-Adresse.");
	        }
			if (secondName == null || secondName.isBlank()) {
				throw new InvalidCustomerDataException("Nachname darf nicht leer sein.");
			}
			if (street == null || street.isBlank()) {
				throw new InvalidCustomerDataException("Straße darf nicht leer sein.");
			}
			
			if (houseNumber == null || houseNumber.isBlank()) {
				
				throw new InvalidCustomerDataException("Hausnummer darf nicht leer sein.");
			}
			
			if (city == null || city.isBlank()) {
				throw new InvalidCustomerDataException("Stadt darf nicht leer sein.");
			}
			if (zipCode == null || zipCode.isBlank()) {
				throw new InvalidCustomerDataException("Postleitzahl darf nicht leer sein.");
			}
			if (landName == null || landName.isBlank()) {
				throw new InvalidCustomerDataException("Land darf nicht leer sein.");
			}
			

	        Customer customer = customerOptional.get();
	        customer.setFirstName(firstName);
	        customer.setSecondName(secondName);
	        customer.setEmail(email);
	        customer.getAddress().setStreet(street);
	        customer.getAddress().setHouseNumber(houseNumber);
	        customer.getAddress().setCity(city);
	        customer.getAddress().setZipCode(zipCode);
	        customer.getAddress().getLand().setLandName(landName);
	        customerRepository.save(customer);
	        return true;
	    }
	    throw new InvalidCustomerDataException("Kunde mit der angegebenen ID wurde nicht gefunden.");
	}

	
	public Long findCustomerById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			return customer.get().getId();
		} else {
			throw new IllegalArgumentException("Kunde mit ID " + id + " nicht gefunden.");
		}
	}
	

	// Example method to retrieve a customer by ID
	public CustomerDto getCustomerById(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Kunde mit ID " + id + " nicht gefunden."));
		
		return customerMapper.toDto(customer);
	}
	
	
	

	// Additional methods can be added as needed for customer management
	private boolean isValidEmail(String email) {
	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    return email != null && email.matches(emailRegex);
	}
	
	// find all
	@Transactional
	public List<CustomerDto> getAllCustomers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Customer> customers = customerRepository.findAll(pageable);
		return customers.getContent(). stream().map(customerMapper::toDto).collect(Collectors.toList());
	}


	public boolean deleteCustomer(Long id) {
		Optional<Customer> customerOptional = customerRepository.findById(id);
		if (customerOptional.isPresent()) {
			customerRepository.delete(customerOptional.get());
			return true;
		}
		throw new IllegalArgumentException("Kunde mit ID " + id + " nicht gefunden.");
		
	}
}
