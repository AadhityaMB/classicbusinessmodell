package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Service interface for payment operations
public interface PaymentService {

    // Fetch all payments with pagination
    Page<PaymentResponseDTO> getAllPayments(Pageable pageable);

    // Fetch payments by customer number
    List<PaymentResponseDTO> getPaymentsByCustomer(Integer customerNumber);

    // Fetch single payment using check number
    PaymentResponseDTO getPaymentByCheckNumber(String checkNumber);

}