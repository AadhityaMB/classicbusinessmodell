package com.classicbusinessmodel_schema.backend.module.customer.service;


import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    // Get all customers
    Page<CustomerResponseDTO> getAllCustomers(Pageable pageable);
    // Get single customer by ID
    CustomerResponseDTO getCustomerById(Integer customerNumber);

    // Create new customer
    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

    // Update existing customer
    CustomerResponseDTO updateCustomer(Integer customerNumber, CustomerRequestDTO request);

    // Delete customer
    void deleteCustomer(Integer customerNumber);

    // Get credit limit
    BigDecimal getCreditLimit(Integer customerNumber);

    // Update only credit limit
    CustomerResponseDTO updateCreditLimit(Integer customerNumber, BigDecimal newLimit);

    // Search by country/city
    List<CustomerResponseDTO> searchByGeography(String country, String city);
}