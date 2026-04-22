package com.classicbusinessmodel_schema.backend.module.report.service;

import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;
import com.classicbusinessmodel_schema.backend.module.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    // --- MOCK PROJECTIONS ---
    private ReportRepository.CustomerExposureProjection createCustomerExposureProj(Integer id, String name, BigDecimal limit, BigDecimal total) {
        return new ReportRepository.CustomerExposureProjection() {
            @Override public Integer getCustomerNumber() { return id; }
            @Override public String getCustomerName() { return name; }
            @Override public BigDecimal getCreditLimit() { return limit; }
            @Override public BigDecimal getTotalOrderValue() { return total; }
        };
    }

    private ReportRepository.SalesByCountryProjection createSalesByCountryProj(String country, BigDecimal total) {
        return new ReportRepository.SalesByCountryProjection() {
            @Override public String getCountry() { return country; }
            @Override public BigDecimal getTotalSales() { return total; }
        };
    }

    private ReportRepository.SalesByEmployeeProjection createSalesByEmployeeProj(Integer id, String name, BigDecimal total) {
        return new ReportRepository.SalesByEmployeeProjection() {
            @Override public Integer getEmployeeNumber() { return id; }
            @Override public String getEmployeeName() { return name; }
            @Override public BigDecimal getTotalSales() { return total; }
        };
    }

    private ReportRepository.MonthlyRevenueProjection createMonthlyRevenueProj(Integer year, Integer month, BigDecimal revenue) {
        return new ReportRepository.MonthlyRevenueProjection() {
            @Override public Integer getYear() { return year; }
            @Override public Integer getMonth() { return month; }
            @Override public BigDecimal getRevenue() { return revenue; }
        };
    }

    // 1. CUSTOMER EXPOSURE
    @Test
    void testGetCustomerExposure() {
        var proj = createCustomerExposureProj(1, "Test Corp", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
        Page<ReportRepository.CustomerExposureProjection> page = new PageImpl<>(List.of(proj));

        when(reportRepository.getCustomerExposure(any(Pageable.class))).thenReturn(page);

        List<CustomerExposureResponseDTO> result = reportService.getCustomerExposure();

        assertEquals(1, result.size());
        CustomerExposureResponseDTO dto = result.get(0);
        assertEquals(1, dto.getCustomerNumber());
        assertEquals("Test Corp", dto.getCustomerName());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(dto.getTotalOrderValue()));
        assertEquals(0, BigDecimal.valueOf(10000).compareTo(dto.getCreditLimit()));
        assertEquals(0, BigDecimal.valueOf(9000).compareTo(dto.getRemainingCredit()));
    }

    // 2. ORDER VALUE
    @Test
    void testGetOrderValue() {
        when(ordersRepository.existsById(1001)).thenReturn(true);
        when(reportRepository.getOrderValue(1001)).thenReturn(BigDecimal.valueOf(1000));

        OrderValueResponseDTO result = reportService.getOrderValue(1001);

        assertNotNull(result);
        assertEquals(1001, result.getOrderNumber());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(result.getTotalValue()));
    }

    @Test
    void testGetOrderValue_Empty() {
        when(ordersRepository.existsById(9999)).thenReturn(true);
        when(reportRepository.getOrderValue(9999)).thenReturn(null);

        OrderValueResponseDTO result = reportService.getOrderValue(9999);

        assertEquals(0, BigDecimal.ZERO.compareTo(result.getTotalValue()));
    }

    @Test
    void testGetOrderValue_NotFound() {
        when(ordersRepository.existsById(9999)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            reportService.getOrderValue(9999);
        });
    }

    // 4. SALES BY COUNTRY
    @Test
    void testGetSalesByCountry() {
        var proj = createSalesByCountryProj("India", BigDecimal.valueOf(1000));
        Page<ReportRepository.SalesByCountryProjection> page = new PageImpl<>(List.of(proj));

        when(reportRepository.getSalesByCountry(any(Pageable.class))).thenReturn(page);

        List<SalesByCountryResponseDTO> result = reportService.getSalesByCountry();

        assertEquals(1, result.size());
        assertEquals("India", result.get(0).getCountry());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(result.get(0).getTotalSales()));
    }

    // 5. SALES BY EMPLOYEE
    @Test
    void testGetSalesByEmployee() {
        var proj = createSalesByEmployeeProj(101, "John Doe", BigDecimal.valueOf(1000));
        Page<ReportRepository.SalesByEmployeeProjection> page = new PageImpl<>(List.of(proj));

        when(reportRepository.getSalesByEmployee(any(Pageable.class))).thenReturn(page);

        List<SalesByEmployeeResponseDTO> result = reportService.getSalesByEmployee();

        assertEquals(1, result.size());
        SalesByEmployeeResponseDTO dto = result.get(0);
        assertEquals(101, dto.getEmployeeNumber());
        assertEquals("John Doe", dto.getEmployeeName());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(dto.getTotalSales()));
    }

    // 7. MONTHLY REVENUE
    @Test
    void testMonthlyRevenue() {
        var proj = createMonthlyRevenueProj(2024, 3, BigDecimal.valueOf(1000));
        Page<ReportRepository.MonthlyRevenueProjection> page = new PageImpl<>(List.of(proj));

        when(reportRepository.getMonthlyRevenue(any(Pageable.class))).thenReturn(page);

        List<MonthlyRevenueResponseDTO> result = reportService.getMonthlyRevenue();

        assertEquals(1, result.size());
        MonthlyRevenueResponseDTO dto = result.get(0);
        assertEquals(2024, dto.getYear());
        assertEquals(3, dto.getMonth());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(dto.getRevenue()));
    }

    // 9. HIGH RISK - NONE
    @Test
    void testHighRisk_None() {
        Page<ReportRepository.CustomerExposureProjection> page = new PageImpl<>(List.of());

        when(reportRepository.getHighRiskCustomers(any(Pageable.class))).thenReturn(page);

        List<HighRiskCustomerResponseDTO> result = reportService.getHighRiskCustomers();

        assertTrue(result.isEmpty());
    }

    // 10. HIGH RISK - ONE
    @Test
    void testHighRisk_One() {
        var proj = createCustomerExposureProj(1, "Test Corp", BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        Page<ReportRepository.CustomerExposureProjection> page = new PageImpl<>(List.of(proj));

        when(reportRepository.getHighRiskCustomers(any(Pageable.class))).thenReturn(page);

        List<HighRiskCustomerResponseDTO> result = reportService.getHighRiskCustomers();

        assertEquals(1, result.size());
        HighRiskCustomerResponseDTO dto = result.get(0);
        assertEquals("Test Corp", dto.getCustomerName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(dto.getRiskPercentage())); // 500 excess / 500 limit * 100 = 100%
    }
}