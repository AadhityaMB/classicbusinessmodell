package com.classicbusinessmodel_schema.backend.module.report.service;


import java.util.List;
import java.util.Map;

public interface ReportService {

    List<Map<String, Object>> getCustomerExposure();

    Double getOrderValue(Integer orderNumber);

    List<Map<String, Object>> getSalesByCountry();

    List<Map<String, Object>> getSalesByEmployee();

    List<Map<String, Object>> getMonthlyRevenue();

    List<Map<String, Object>> getHighRiskCustomers();
}
