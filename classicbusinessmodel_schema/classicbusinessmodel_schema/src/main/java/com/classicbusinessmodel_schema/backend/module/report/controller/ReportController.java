package com.classicbusinessmodel_schema.backend.module.report.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;
import com.classicbusinessmodel_schema.backend.module.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 1. Customer Exposure
    @GetMapping("/customer-exposure")
    public ResponseEntity<ApiResponse<List<CustomerExposureResponseDTO>>> getCustomerExposure() {

        List<CustomerExposureResponseDTO> data = reportService.getCustomerExposure();

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerExposureResponseDTO>>builder()
                        .message("Customer exposure report fetched successfully")
                        .status(200)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // 2. Order Value
    @GetMapping("/order-value/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderValueResponseDTO>> getOrderValue(
            @PathVariable Integer orderNumber) {

        OrderValueResponseDTO data = reportService.getOrderValue(orderNumber);

        return ResponseEntity.ok(
                ApiResponse.<OrderValueResponseDTO>builder()
                        .message("Order value fetched successfully")
                        .status(200)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // 3. Sales By Country
    @GetMapping("/sales-by-country")
    public ResponseEntity<ApiResponse<List<SalesByCountryResponseDTO>>> getSalesByCountry() {

        List<SalesByCountryResponseDTO> data = reportService.getSalesByCountry();

        return ResponseEntity.ok(
                ApiResponse.<List<SalesByCountryResponseDTO>>builder()
                        .message("Sales by country fetched successfully")
                        .status(200)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // 4. Sales By Employee
    @GetMapping("/sales-by-employee")
    public ResponseEntity<ApiResponse<List<SalesByEmployeeResponseDTO>>> getSalesByEmployee() {

        List<SalesByEmployeeResponseDTO> data = reportService.getSalesByEmployee();

        return ResponseEntity.ok(
                ApiResponse.<List<SalesByEmployeeResponseDTO>>builder()
                        .message("Sales by employee fetched successfully")
                        .status(200)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // 5. Monthly Revenue
    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<MonthlyRevenueResponseDTO>>> getMonthlyRevenue() {

        List<MonthlyRevenueResponseDTO> data = reportService.getMonthlyRevenue();

        return ResponseEntity.ok(
                ApiResponse.<List<MonthlyRevenueResponseDTO>>builder()
                        .message("Monthly revenue fetched successfully")
                        .status(200)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // 6. High Risk Customers
    @GetMapping("/high-risk-customers")
    public ResponseEntity<ApiResponse<List<HighRiskCustomerResponseDTO>>> getHighRiskCustomers() {

        List<HighRiskCustomerResponseDTO> data = reportService.getHighRiskCustomers();

        return ResponseEntity.ok(
                ApiResponse.<List<HighRiskCustomerResponseDTO>>builder()
                        .message("High risk customers fetched successfully")
                        .status(200)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}