package com.classicbusinessmodel_schema.backend.module.customer.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "APIs for managing payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // GET ALL (with pagination)
    @Operation(summary = "Get all payments", description = "Fetch all payments with pagination")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

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

    // GET BY CUSTOMER
    @Operation(summary = "Get payments by customer", description = "Fetch payments using customer number")
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
    @Operation(summary = "Get payment by check number", description = "Fetch payment using check number")
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