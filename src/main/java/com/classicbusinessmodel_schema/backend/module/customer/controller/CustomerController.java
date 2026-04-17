package com.classicbusinessmodel_schema.backend.module.customer.controller;


import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CreditLimitRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.CustomerResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Create customer
    @Operation(summary = "Create a new customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    // Get all customers (pagination + sorting)
    @Operation(summary = "Get all customers (paginated)")
    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "customerName") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return ResponseEntity.ok(
                customerService.getAllCustomers(pageable));
    }

    // Get customer by ID
    @GetMapping("/{customerNumber}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable Integer customerNumber) {
        return ResponseEntity.ok(
                customerService.getCustomerById(customerNumber));
    }

    // Update customer
    @Operation(summary = "Update customer")
    @PutMapping("/{customerNumber}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CustomerRequestDTO request) {
        return ResponseEntity.ok(
                customerService.updateCustomer(customerNumber, request));
    }

    // Delete customer
    @Operation(summary = "Delete customer")
    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer customerNumber) {
        customerService.deleteCustomer(customerNumber);
        return ResponseEntity.noContent().build();
    }

    // Get credit limit
    @Operation(summary = "Get customer credit limit")
    @GetMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<BigDecimal> getCreditLimit(
            @PathVariable Integer customerNumber) {
        return ResponseEntity.ok(
                customerService.getCreditLimit(customerNumber));
    }

    // Update credit limit
    @Operation(summary = "Update customer credit limit")
    @PutMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<CustomerResponseDTO> updateCreditLimit(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CreditLimitRequestDTO request) {
        return ResponseEntity.ok(
                customerService.updateCreditLimit(customerNumber, request.getCreditLimit()));
    }

    // Search customers
    @Operation(summary = "Search customers by country and/or city")
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomers(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(
                customerService.searchByGeography(country, city));
    }
}