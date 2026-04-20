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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

            CustomerExposureResponseDTO dto = new CustomerExposureResponseDTO();
            dto.setCustomerNumber(customer.getCustomerNumber());
            dto.setCustomerName(customer.getCustomerName());
            dto.setCreditLimit(credit);
            dto.setTotalOrderValue(total);
            dto.setRemainingCredit(credit.subtract(total));

            return dto;

        }).toList();
    }

    // 2. ORDER VALUE
    @Override
    public OrderValueResponseDTO getOrderValue(Integer orderNumber) {

        double total = calculateOrderValue(orderNumber);

        OrderValueResponseDTO dto = new OrderValueResponseDTO();
        dto.setOrderNumber(orderNumber);
        dto.setTotalValue(BigDecimal.valueOf(total));

        return dto;
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
                .map(entry -> {
                    SalesByCountryResponseDTO dto = new SalesByCountryResponseDTO();
                    dto.setCountry(entry.getKey());
                    dto.setTotalSales(BigDecimal.valueOf(entry.getValue()));
                    return dto;
                })
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
                .map(entry -> {
                    SalesByEmployeeResponseDTO dto = new SalesByEmployeeResponseDTO();
                    dto.setEmployeeNumber(entry.getKey());
                    dto.setEmployeeName(employeeNames.get(entry.getKey()));
                    dto.setTotalSales(BigDecimal.valueOf(entry.getValue()));
                    return dto;
                })
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

                    MonthlyRevenueResponseDTO dto = new MonthlyRevenueResponseDTO();
                    dto.setYear(Integer.parseInt(parts[0]));
                    dto.setMonth(Integer.parseInt(parts[1]));
                    dto.setRevenue(BigDecimal.valueOf(entry.getValue()));

                    return dto;
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

                    HighRiskCustomerResponseDTO dto = new HighRiskCustomerResponseDTO();
                    dto.setCustomerNumber(c.getCustomerNumber());
                    dto.setCustomerName(c.getCustomerName());
                    dto.setCreditLimit(c.getCreditLimit());
                    dto.setTotalOrderValue(c.getTotalOrderValue());
                    dto.setRiskPercentage(riskPercentage);

                    return dto;
                })
                .toList();
    }

    // HELPER METHOD
    private double calculateOrderValue(Integer orderNumber) {

        List<OrderDetails> items =
                orderDetailRepository.findByOrderOrderNumber(orderNumber);

        if (items.isEmpty()) {
            return 0; // FIXED
        }

        return items.stream()
                .mapToDouble(i -> i.getQuantityOrdered() * i.getPriceEach().doubleValue())
                .sum();
    }
}