package com.classicbusinessmodel_schema.backend.module.customer.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CreditLimitRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.CustomerResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // CREATE CUSTOMER API
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {

        // call service to create customer
        CustomerResponseDTO response = customerService.createCustomer(request);

        // return standard API response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Customer created successfully",
                        response
                ));
    }

    // GET ALL CUSTOMERS (with pagination)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponseDTO>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "customerName") String sortBy) {

        // pagination and sorting setup
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // fetch data from service
        Page<CustomerResponseDTO> result = customerService.getAllCustomers(pageable);

        // return response
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customers fetched successfully",
                        result
                )
        );
    }

    // GET CUSTOMER BY ID
    @GetMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomerById(
            @PathVariable Integer customerNumber) {

        CustomerResponseDTO response =
                customerService.getCustomerById(customerNumber);

        ApiResponse<CustomerResponseDTO> apiResponse =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customer fetched successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }
    // UPDATE CUSTOMER
    @PutMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCustomer(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CustomerRequestDTO request) {

        // update customer details
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

    // DELETE CUSTOMER
    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Integer customerNumber) {

        // delete customer by id
        customerService.deleteCustomer(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Customer deleted successfully",
                        null
                )
        );
    }

    // GET CREDIT LIMIT
    @GetMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<ApiResponse<BigDecimal>> getCreditLimit(
            @PathVariable Integer customerNumber) {

        // fetch credit limit
        BigDecimal creditLimit = customerService.getCreditLimit(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Credit limit fetched successfully",
                        creditLimit
                )
        );
    }

    // UPDATE CREDIT LIMIT
    @PutMapping("/{customerNumber}/credit-limit")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCreditLimit(
            @PathVariable Integer customerNumber,
            @Valid @RequestBody CreditLimitRequestDTO request) {

        // update credit limit
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

    // SEARCH CUSTOMERS BY COUNTRY / CITY
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> searchCustomers(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city) {

        // search based on filters
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