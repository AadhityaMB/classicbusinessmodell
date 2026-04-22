package com.classicbusinessmodel_schema.backend.module.report.controller;


import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;
import com.classicbusinessmodel_schema.backend.module.report.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    private <T> ResponseEntity<ApiResponse<Page<T>>> success(String message, Page<T> data) {
        return ResponseEntity.ok(new ApiResponse<>(200, message, data));
    }

    // 1. Customer Exposure
    @GetMapping("/customer-exposure")
    public ResponseEntity<ApiResponse<Page<CustomerExposureResponseDTO>>> getCustomerExposure(
            @PageableDefault(size = 10) Pageable pageable) {

        return success("Customer exposure report fetched successfully",
                reportService.getCustomerExposure(pageable));
    }

    // 2. Order Value (no pagination needed)
    @GetMapping("/order-value/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderValueResponseDTO>> getOrderValue(
            @PathVariable Integer orderNumber) {

        return ResponseEntity.ok(
                new ApiResponse<>(200,
                        "Order value fetched successfully",
                        reportService.getOrderValue(orderNumber))
        );
    }

    // 3. Sales By Country
    @GetMapping("/sales-by-country")
    public ResponseEntity<ApiResponse<Page<SalesByCountryResponseDTO>>> getSalesByCountry(
            @PageableDefault(size = 10) Pageable pageable) {

        return success("Sales by country fetched successfully",
                reportService.getSalesByCountry(pageable));
    }

    // 4. Sales By Employee
    @GetMapping("/sales-by-employee")
    public ResponseEntity<ApiResponse<Page<SalesByEmployeeResponseDTO>>> getSalesByEmployee(
            @PageableDefault(size = 10) Pageable pageable) {

        return success("Sales by employee fetched successfully",
                reportService.getSalesByEmployee(pageable));
    }

    // 5. Monthly Revenue
    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<Page<MonthlyRevenueResponseDTO>>> getMonthlyRevenue(
            @PageableDefault(size = 10) Pageable pageable) {

        return success("Monthly revenue fetched successfully",
                reportService.getMonthlyRevenue(pageable));
    }

    // 6. High Risk Customers
    @GetMapping("/high-risk-customers")
    public ResponseEntity<ApiResponse<Page<HighRiskCustomerResponseDTO>>> getHighRiskCustomers(
            @PageableDefault(size = 10) Pageable pageable) {

        return success("High risk customers fetched successfully",
                reportService.getHighRiskCustomers(pageable));
    }
}