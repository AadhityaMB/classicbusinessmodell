package com.classicbusinessmodel_schema.backend.module.report.service;

import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {

    // PAGINATED METHODS (used by controller)
    Page<CustomerExposureResponseDTO> getCustomerExposure(Pageable pageable);

    Page<SalesByCountryResponseDTO> getSalesByCountry(Pageable pageable);

    Page<SalesByEmployeeResponseDTO> getSalesByEmployee(Pageable pageable);

    Page<MonthlyRevenueResponseDTO> getMonthlyRevenue(Pageable pageable);

    Page<HighRiskCustomerResponseDTO> getHighRiskCustomers(Pageable pageable);

    // NON-PAGINATED (used by tests)
    List<CustomerExposureResponseDTO> getCustomerExposure();

    List<SalesByCountryResponseDTO> getSalesByCountry();

    List<SalesByEmployeeResponseDTO> getSalesByEmployee();

    List<MonthlyRevenueResponseDTO> getMonthlyRevenue();

    List<HighRiskCustomerResponseDTO> getHighRiskCustomers();

    // NORMAL METHOD
    OrderValueResponseDTO getOrderValue(Integer orderNumber);
}