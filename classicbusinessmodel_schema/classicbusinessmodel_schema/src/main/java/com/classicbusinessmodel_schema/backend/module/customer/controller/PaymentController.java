package com.classicbusinessmodel_schema.backend.module.customer.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.PaymentRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 1. GET - Payments by Customer
    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<ApiResponse<List<Payment>>> getPaymentsByCustomer(
            @PathVariable Integer customerNumber) {

        List<Payment> payments = paymentService.getPaymentsByCustomer(customerNumber);

        ApiResponse<List<Payment>> response = new ApiResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Payments fetched successfully for customer " + customerNumber,
                payments
        );

        return ResponseEntity.ok(response);
    }

    // 2. GET - Payments by Date Range
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<Payment>>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Payment> payments = paymentService.getPaymentsByDateRange(startDate, endDate);

        ApiResponse<List<Payment>> response = new ApiResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Payments fetched between " + startDate + " and " + endDate,
                payments
        );

        return ResponseEntity.ok(response);
    }

    // 3. GET - Single Payment
    @GetMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<ApiResponse<Payment>> getPayment(
            @PathVariable Integer customerNumber,
            @PathVariable String checkNumber) {

        Payment payment = paymentService.getPayment(customerNumber, checkNumber);

        ApiResponse<Payment> response = new ApiResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Payment fetched successfully",
                payment
        );

        return ResponseEntity.ok(response);
    }

    // 4. PUT - Update Payment Amount
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updatePaymentAmount(
            @RequestParam Integer customerNumber,
            @RequestParam String checkNumber,
            @RequestParam BigDecimal amount) {

        paymentService.updatePaymentAmount(customerNumber, checkNumber, amount);

        ApiResponse<String> response = new ApiResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Payment amount updated successfully",
                "UPDATED"
        );

        return ResponseEntity.ok(response);
    }

    // 5. DELETE - Delete Payment
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deletePayment(
            @RequestParam Integer customerNumber,
            @RequestParam String checkNumber) {

        paymentService.deletePayment(customerNumber, checkNumber);

        ApiResponse<String> response = new ApiResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Payment deleted successfully",
                "DELETED"
        );

        return ResponseEntity.ok(response);
    }
}