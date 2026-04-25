package com.classicbusinessmodel_schema.backend.module.report.service;


import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;

import java.util.List;
import java.util.Map;

public interface ReportService {

    List<CustomerExposureResponseDTO> getCustomerExposure();

    OrderValueResponseDTO getOrderValue(Integer orderNumber);

    List<SalesByCountryResponseDTO> getSalesByCountry();

    List<SalesByEmployeeResponseDTO> getSalesByEmployee();

    List<MonthlyRevenueResponseDTO> getMonthlyRevenue();

    List<HighRiskCustomerResponseDTO> getHighRiskCustomers();
}
