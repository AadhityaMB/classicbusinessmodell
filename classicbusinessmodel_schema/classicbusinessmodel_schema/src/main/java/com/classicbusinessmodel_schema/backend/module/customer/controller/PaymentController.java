package com.classicbusinessmodel_schema.backend.module.customer.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getAll() {

        List<PaymentResponseDTO> payments = paymentService.getAllPayments();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "All payments fetched",
                        payments
                )
        );
    }

    // GET BY CUSTOMER
    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getByCustomer(@PathVariable Integer customerNumber) {

        List<PaymentResponseDTO> payments = paymentService.getPaymentsByCustomer(customerNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Payments fetched by customer",
                        payments
                )
        );
    }

    // GET BY CHECK NUMBER
    @GetMapping("/check/{checkNumber}")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> getByCheck(@PathVariable String checkNumber) {

        PaymentResponseDTO payment = paymentService.getPaymentByCheckNumber(checkNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Payment fetched",
                        payment
                )
        );
    }

}