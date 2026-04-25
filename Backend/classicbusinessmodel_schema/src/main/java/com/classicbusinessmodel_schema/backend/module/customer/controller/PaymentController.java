package com.classicbusinessmodel_schema.backend.module.customer.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as REST controller
@RestController

// Base URL for payment APIs
@RequestMapping("/api/payments")

@Tag(name = "Payment", description = "APIs for managing payments")
public class PaymentController {

    // Injects PaymentService
    @Autowired
    private PaymentService paymentService;

    // Handles GET request to fetch all payments with pagination
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Calls service with pagination
        Page<PaymentResponseDTO> paymentsPage =
                paymentService.getAllPayments(PageRequest.of(page, size));

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "All payments fetched",
                        paymentsPage.getContent()
                )
        );
    }

    // Fetch payments by customer number
    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getByCustomer(
            @PathVariable Integer customerNumber) {

        // Calls service layer
        List<PaymentResponseDTO> payments =
                paymentService.getPaymentsByCustomer(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Payments fetched by customer",
                        payments
                )
        );
    }

    // Fetch payment using check number
    @GetMapping("/check/{checkNumber}")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> getByCheck(
            @PathVariable String checkNumber) {

        // Calls service to fetch payment
        PaymentResponseDTO payment =
                paymentService.getPaymentByCheckNumber(checkNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Payment fetched",
                        payment
                )
        );
    }
}