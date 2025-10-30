
package com.dam.k_ecommerce.controller;

import com.dam.k_ecommerce.dto.CustomerDto;
import com.dam.k_ecommerce.model.Customer;
import com.dam.k_ecommerce.repository.CustomerRepository;
import com.dam.k_ecommerce.services.CustomerService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.createCustomer(
            customerRequest.getFirstName(),
            customerRequest.getSecondName(),
            customerRequest.getEmail(),
            customerRequest.getStreet(),
            customerRequest.getHouseNumber(),
            customerRequest.getCity(),
            customerRequest.getZipCode(),
            customerRequest.getLandName()
        );
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        CustomerDto customerDto = customerService.getCustomerById(id);
        if (customerDto != null) {
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Long> findCustomerById(@PathVariable("id") Long id) {
        log.info("I am in customer Controller. Received request to find customer by ID: {}", id);
        try {
            Long customerId = customerRepository.findById(id)
                .map(Customer::getId)
                .orElseThrow(() -> new IllegalArgumentException("Kunde mit ID " + id + " nicht gefunden."));
            log.info("Customer found with ID: {}", customerId);
            return new ResponseEntity<>(customerId, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Customer not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomersPaginated(@RequestParam int page, @RequestParam int size) {
        List<CustomerDto> customers = customerService.getAllCustomers(page, size);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest) {
        boolean isUpdated = customerService.updateCustomer(
            id,
            customerRequest.getFirstName(),
            customerRequest.getSecondName(),
            customerRequest.getEmail(),
            customerRequest.getStreet(),
            customerRequest.getHouseNumber(),
            customerRequest.getCity(),
            customerRequest.getZipCode(),
            customerRequest.getLandName()
        );

        if (isUpdated) {
            return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }
}
