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
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 1. Customer Exposure
    @GetMapping("/customer-exposure")
    public ResponseEntity<ApiResponse<List<CustomerExposureResponseDTO>>> getCustomerExposure() {

        List<CustomerExposureResponseDTO> data = reportService.getCustomerExposure();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Customer exposure report fetched successfully",
                        data
                )
        );
    }

    // 2. Order Value
    @GetMapping("/order-value/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderValueResponseDTO>> getOrderValue(
            @PathVariable Integer orderNumber) {

        OrderValueResponseDTO data = reportService.getOrderValue(orderNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order value fetched successfully",
                        data
                )
        );
    }

    // 3. Sales By Country
    @GetMapping("/sales-by-country")
    public ResponseEntity<ApiResponse<List<SalesByCountryResponseDTO>>> getSalesByCountry() {

        List<SalesByCountryResponseDTO> data = reportService.getSalesByCountry();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Sales by country fetched successfully",
                        data
                )
        );
    }

    // 4. Sales By Employee
    @GetMapping("/sales-by-employee")
    public ResponseEntity<ApiResponse<List<SalesByEmployeeResponseDTO>>> getSalesByEmployee() {

        List<SalesByEmployeeResponseDTO> data = reportService.getSalesByEmployee();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Sales by employee fetched successfully",
                        data
                )
        );
    }

    // 5. Monthly Revenue
    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<MonthlyRevenueResponseDTO>>> getMonthlyRevenue() {

        List<MonthlyRevenueResponseDTO> data = reportService.getMonthlyRevenue();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Monthly revenue fetched successfully",
                        data
                )
        );
    }

    // 6. High Risk Customers
    @GetMapping("/high-risk-customers")
    public ResponseEntity<ApiResponse<List<HighRiskCustomerResponseDTO>>> getHighRiskCustomers() {

        List<HighRiskCustomerResponseDTO> data = reportService.getHighRiskCustomers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "High risk customers fetched successfully",
                        data
                )
        );
    }
}