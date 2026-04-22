package com.classicbusinessmodel_schema.backend.module.report.service;



import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;
import com.classicbusinessmodel_schema.backend.module.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final OrdersRepository ordersRepository;

    // ===============================
    // 1. CUSTOMER EXPOSURE
    // ===============================
    @Override
    public Page<CustomerExposureResponseDTO> getCustomerExposure(Pageable pageable) {
        return reportRepository.getCustomerExposure(pageable).map(this::mapToCustomerExposureDTO);
    }

    @Override
    public List<CustomerExposureResponseDTO> getCustomerExposure() {
        return reportRepository.getCustomerExposure(Pageable.unpaged()).map(this::mapToCustomerExposureDTO).getContent();
    }

    private CustomerExposureResponseDTO mapToCustomerExposureDTO(ReportRepository.CustomerExposureProjection projection) {
        BigDecimal credit = projection.getCreditLimit() != null ? projection.getCreditLimit() : BigDecimal.ZERO;
        BigDecimal total = projection.getTotalOrderValue() != null ? projection.getTotalOrderValue() : BigDecimal.ZERO;
        
        return CustomerExposureResponseDTO.builder()
                .customerNumber(projection.getCustomerNumber())
                .customerName(projection.getCustomerName())
                .creditLimit(credit)
                .totalOrderValue(total)
                .remainingCredit(credit.subtract(total).max(BigDecimal.ZERO))
                .build();
    }

    // ===============================
    // 2. ORDER VALUE
    // ===============================
    @Override
    public OrderValueResponseDTO getOrderValue(Integer orderNumber) {
        if (!ordersRepository.existsById(orderNumber)) {
            throw new ResourceNotFoundException("Order with order number " + orderNumber + " not found");
        }

        BigDecimal totalValue = reportRepository.getOrderValue(orderNumber);
        if (totalValue == null) {
            totalValue = BigDecimal.ZERO;
        }

        return OrderValueResponseDTO.builder()
                .orderNumber(orderNumber)
                .totalValue(totalValue)
                .build();
    }

    // ===============================
    // 3. SALES BY COUNTRY
    // ===============================
    @Override
    public Page<SalesByCountryResponseDTO> getSalesByCountry(Pageable pageable) {
        return reportRepository.getSalesByCountry(pageable).map(p -> 
            SalesByCountryResponseDTO.builder()
                .country(p.getCountry())
                .totalSales(p.getTotalSales() != null ? p.getTotalSales() : BigDecimal.ZERO)
                .build()
        );
    }

    @Override
    public List<SalesByCountryResponseDTO> getSalesByCountry() {
        return reportRepository.getSalesByCountry(Pageable.unpaged()).map(p -> 
            SalesByCountryResponseDTO.builder()
                .country(p.getCountry())
                .totalSales(p.getTotalSales() != null ? p.getTotalSales() : BigDecimal.ZERO)
                .build()
        ).getContent();
    }

    // ===============================
    // 4. SALES BY EMPLOYEE
    // ===============================
    @Override
    public Page<SalesByEmployeeResponseDTO> getSalesByEmployee(Pageable pageable) {
        return reportRepository.getSalesByEmployee(pageable).map(p ->
            SalesByEmployeeResponseDTO.builder()
                .employeeNumber(p.getEmployeeNumber())
                .employeeName(p.getEmployeeName())
                .totalSales(p.getTotalSales() != null ? p.getTotalSales() : BigDecimal.ZERO)
                .build()
        );
    }

    @Override
    public List<SalesByEmployeeResponseDTO> getSalesByEmployee() {
        return reportRepository.getSalesByEmployee(Pageable.unpaged()).map(p ->
            SalesByEmployeeResponseDTO.builder()
                .employeeNumber(p.getEmployeeNumber())
                .employeeName(p.getEmployeeName())
                .totalSales(p.getTotalSales() != null ? p.getTotalSales() : BigDecimal.ZERO)
                .build()
        ).getContent();
    }

    // ===============================
    // 5. MONTHLY REVENUE
    // ===============================
    @Override
    public Page<MonthlyRevenueResponseDTO> getMonthlyRevenue(Pageable pageable) {
        return reportRepository.getMonthlyRevenue(pageable).map(p ->
            MonthlyRevenueResponseDTO.builder()
                .year(p.getYear())
                .month(p.getMonth())
                .revenue(p.getRevenue() != null ? p.getRevenue() : BigDecimal.ZERO)
                .build()
        );
    }

    @Override
    public List<MonthlyRevenueResponseDTO> getMonthlyRevenue() {
        return reportRepository.getMonthlyRevenue(Pageable.unpaged()).map(p ->
            MonthlyRevenueResponseDTO.builder()
                .year(p.getYear())
                .month(p.getMonth())
                .revenue(p.getRevenue() != null ? p.getRevenue() : BigDecimal.ZERO)
                .build()
        ).getContent();
    }

    // ===============================
    // 6. HIGH RISK CUSTOMERS
    // ===============================
    @Override
    public Page<HighRiskCustomerResponseDTO> getHighRiskCustomers(Pageable pageable) {
        return reportRepository.getHighRiskCustomers(pageable).map(this::mapToHighRiskCustomerDTO);
    }

    // HELPER METHOD
    private double calculateOrderValue(Integer orderNumber) {

        List<OrderDetails> items =
                orderDetailRepository.findByOrderOrderNumber(orderNumber);

        if (items.isEmpty()) {
            return 0;
        }

    @Override
    public List<HighRiskCustomerResponseDTO> getHighRiskCustomers() {
        return reportRepository.getHighRiskCustomers(Pageable.unpaged()).map(this::mapToHighRiskCustomerDTO).getContent();
  

    private HighRiskCustomerResponseDTO mapToHighRiskCustomerDTO(ReportRepository.CustomerExposureProjection p) {
        BigDecimal excess = p.getTotalOrderValue().subtract(p.getCreditLimit());
        BigDecimal riskPercentage = excess
                .multiply(BigDecimal.valueOf(100))
                .divide(p.getCreditLimit(), 2, RoundingMode.HALF_UP);

        HighRiskCustomerResponseDTO dto = new HighRiskCustomerResponseDTO();
        dto.setCustomerNumber(p.getCustomerNumber());
        dto.setCustomerName(p.getCustomerName());
        dto.setCreditLimit(p.getCreditLimit());
        dto.setTotalOrderValue(p.getTotalOrderValue());
        dto.setRiskPercentage(riskPercentage);
        return dto;
    }
}
