package com.classicbusinessmodel_schema.backend.module.report.service;


import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import com.classicbusinessmodel_schema.backend.module.report.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CustomerRepository customerRepository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;

    // 1. CUSTOMER EXPOSURE
    @Override
    public List<CustomerExposureResponseDTO> getCustomerExposure() {

        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customer -> {

            List<Orders> orders =
                    ordersRepository.findByCustomerCustomerNumber(customer.getCustomerNumber());

            double totalOrderValue = orders.stream()
                    .mapToDouble(order -> calculateOrderValue(order.getOrderNumber()))
                    .sum();

            BigDecimal credit = customer.getCreditLimit() != null
                    ? customer.getCreditLimit()
                    : BigDecimal.ZERO;

            BigDecimal total = BigDecimal.valueOf(totalOrderValue);

            return CustomerExposureResponseDTO.builder()
                    .customerNumber(customer.getCustomerNumber())
                    .customerName(customer.getCustomerName())
                    .creditLimit(credit)
                    .totalOrderValue(total)
                    .remainingCredit(credit.subtract(total))
                    .build();

        }).toList();
    }

    // 2. ORDER VALUE
    @Override
    public OrderValueResponseDTO getOrderValue(Integer orderNumber) {

        double total = calculateOrderValue(orderNumber);

        return OrderValueResponseDTO.builder()
                .orderNumber(orderNumber)
                .totalValue(BigDecimal.valueOf(total))
                .build();
    }

    // 3. SALES BY COUNTRY
    @Override
    public List<SalesByCountryResponseDTO> getSalesByCountry() {

        Map<String, Double> map = new HashMap<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer c : customers) {

            List<Orders> orders =
                    ordersRepository.findByCustomerCustomerNumber(c.getCustomerNumber());

            double total = orders.stream()
                    .mapToDouble(o -> calculateOrderValue(o.getOrderNumber()))
                    .sum();

            map.merge(c.getCountry(), total, Double::sum);
        }

        return map.entrySet().stream()
                .map(entry -> SalesByCountryResponseDTO.builder()
                        .country(entry.getKey())
                        .totalSales(BigDecimal.valueOf(entry.getValue()))
                        .build())
                .toList();
    }

    // 4. SALES BY EMPLOYEE
    @Override
    public List<SalesByEmployeeResponseDTO> getSalesByEmployee() {

        Map<Integer, Double> map = new HashMap<>();
        Map<Integer, String> employeeNames = new HashMap<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer c : customers) {

            if (c.getSalesRep() == null) continue;

            Integer empId = c.getSalesRep().getEmployeeNumber();
            String empName = c.getSalesRep().getFirstName() + " " + c.getSalesRep().getLastName();

            employeeNames.put(empId, empName);

            List<Orders> orders =
                    ordersRepository.findByCustomerCustomerNumber(c.getCustomerNumber());

            double total = orders.stream()
                    .mapToDouble(o -> calculateOrderValue(o.getOrderNumber()))
                    .sum();

            map.merge(empId, total, Double::sum);
        }

        return map.entrySet().stream()
                .map(entry -> SalesByEmployeeResponseDTO.builder()
                        .employeeNumber(entry.getKey())
                        .employeeName(employeeNames.get(entry.getKey()))
                        .totalSales(BigDecimal.valueOf(entry.getValue()))
                        .build())
                .toList();
    }

    // 5. MONTHLY REVENUE
    @Override
    public List<MonthlyRevenueResponseDTO> getMonthlyRevenue() {

        Map<String, Double> map = new HashMap<>();

        List<Orders> orders = ordersRepository.findAll();

        for (Orders o : orders) {

            if (o.getOrderDate() == null) continue;

            String key = o.getOrderDate().getYear() + "-" + o.getOrderDate().getMonthValue();

            double value = calculateOrderValue(o.getOrderNumber());

            map.merge(key, value, Double::sum);
        }

        return map.entrySet().stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("-");
                    return MonthlyRevenueResponseDTO.builder()
                            .year(Integer.parseInt(parts[0]))
                            .month(Integer.parseInt(parts[1]))
                            .revenue(BigDecimal.valueOf(entry.getValue()))
                            .build();
                })
                .toList();
    }

    // 6. HIGH RISK CUSTOMERS
    @Override
    public List<HighRiskCustomerResponseDTO> getHighRiskCustomers() {

        return getCustomerExposure().stream()
                .filter(c -> c.getCreditLimit() != null &&
                        c.getCreditLimit().compareTo(BigDecimal.ZERO) > 0)
                .filter(c -> c.getTotalOrderValue().compareTo(c.getCreditLimit()) > 0)
                .map(c -> {

                    BigDecimal excess = c.getTotalOrderValue().subtract(c.getCreditLimit());

                    BigDecimal riskPercentage = excess
                            .multiply(BigDecimal.valueOf(100))
                            .divide(c.getCreditLimit(), 2, RoundingMode.HALF_UP);

                    return HighRiskCustomerResponseDTO.builder()
                            .customerNumber(c.getCustomerNumber())
                            .customerName(c.getCustomerName())
                            .creditLimit(c.getCreditLimit())
                            .totalOrderValue(c.getTotalOrderValue())
                            .riskPercentage(riskPercentage)
                            .build();
                })
                .toList();
    }

    // 🔧 HELPER METHOD
    private double calculateOrderValue(Integer orderNumber) {

        List<OrderDetails> items =
                orderDetailRepository.findByOrderOrderNumber(orderNumber);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }

        return items.stream()
                .mapToDouble(i -> i.getQuantityOrdered() * i.getPriceEach().doubleValue())
                .sum();
    }
}