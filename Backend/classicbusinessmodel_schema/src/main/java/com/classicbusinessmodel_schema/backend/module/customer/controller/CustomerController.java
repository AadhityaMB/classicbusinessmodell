package com.classicbusinessmodel_schema.backend.module.customer.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CreditLimitRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.CustomerResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.CustomerService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// Marks this class as a REST controller
@RestController

// Base URL for all customer APIs
@RequestMapping("/api/customers")

@Tag(name = "Customer", description = "APIs for managing customers")
public class CustomerController {

    // Injects the CustomerService
    @Autowired
    private CustomerService customerService;

    // Handles POST request to create a customer
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {

        // Call service layer
        CustomerResponseDTO response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Customer created successfully",
                        response
                ));
    }

    // Handles GET request with pagination and sorting
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponseDTO>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "customerName") String sortBy) {

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // Fetch customers from service
        Page<CustomerResponseDTO> result = customerService.getAllCustomers(pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customers fetched successfully",
                        result
                )
        );
    }

    // Fetch a customer using ID from URL
    @GetMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomerById(
            @PathVariable Integer customerNumber) {

        CustomerResponseDTO response =
                customerService.getCustomerById(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customer fetched successfully",
                        response
                )
        );
    }

    // Updates an existing customer
    @PutMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCustomer(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CustomerRequestDTO request) {

        // Call service to update
        CustomerResponseDTO response =
                customerService.updateCustomer(customerNumber, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customer updated successfully",
                        response
                )
        );
    }

    // Deletes a customer by ID
    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Integer customerNumber) {

        // Call service to delete
        customerService.deleteCustomer(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customer deleted successfully",
                        null
                )
        );
    }

    // Fetch credit limit of a customer
    @GetMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<ApiResponse<BigDecimal>> getCreditLimit(
            @PathVariable Integer customerNumber) {

        // Get credit limit from service
        BigDecimal creditLimit = customerService.getCreditLimit(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Credit limit fetched successfully",
                        creditLimit
                )
        );
    }

    // Update credit limit of a customer
    @PutMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCreditLimit(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CreditLimitRequestDTO request) {

        // Pass value from request DTO
        CustomerResponseDTO response =
                customerService.updateCreditLimit(customerNumber, request.getCreditLimit());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Credit limit updated successfully",
                        response
                )
        );
    }

    // Search customers by country or city
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> searchCustomers(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city) {

        // Call service with filters
        List<CustomerResponseDTO> result =
                customerService.searchByGeography(country, city);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Search completed successfully",
                        result
                )
        );
    }
}