package com.classicbusinessmodel_schema.backend.module.report.service;


import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final CustomerRepository customerRepository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ReportServiceImpl(CustomerRepository customerRepository,
                             OrdersRepository ordersRepository,
                             OrderDetailRepository orderDetailRepository) {
        this.customerRepository = customerRepository;
        this.ordersRepository = ordersRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    // 1. CUSTOMER EXPOSURE
    @Override
    public List<Map<String, Object>> getCustomerExposure() {

        List<Customer> customers = customerRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Customer customer : customers) {

            List<Orders> orders = ordersRepository
                    .findByCustomerCustomerNumber(customer.getCustomerNumber());

            double totalOrderValue = 0;

            for (Orders order : orders) {
                totalOrderValue += getOrderValue(order.getOrderNumber());
            }

            Map<String, Object> data = new HashMap<>();
            data.put("customerId", customer.getCustomerNumber());
            data.put("customerName", customer.getCustomerName());
            data.put("creditLimit", customer.getCreditLimit());
            data.put("totalOrderValue", totalOrderValue);

            result.add(data);
        }

        return result;
    }

    // 2. ORDER VALUE
    @Override
    public Double getOrderValue(Integer orderNumber) {

        List<OrderDetails> items =
                orderDetailRepository.findByOrderOrderNumber(orderNumber);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }

        return items.stream()
                .mapToDouble(i -> i.getQuantityOrdered() * i.getPriceEach().doubleValue())
                .sum();
    }

    // 3. SALES BY COUNTRY
    @Override
    public List<Map<String, Object>> getSalesByCountry() {

        List<Customer> customers = customerRepository.findAll();
        Map<String, Double> map = new HashMap<>();

        for (Customer c : customers) {

            List<Orders> orders =
                    ordersRepository.findByCustomerCustomerNumber(c.getCustomerNumber());

            double total = 0;
            for (Orders o : orders) {
                total += getOrderValue(o.getOrderNumber());
            }

            map.merge(c.getCountry(), total, Double::sum);
        }

        return convertMap(map);
    }

    // 4. SALES BY EMPLOYEE
    @Override
    public List<Map<String, Object>> getSalesByEmployee() {

        Map<Integer, Double> map = new HashMap<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer c : customers) {

            if (c.getSalesRep() == null) continue;

            Integer empId = c.getSalesRep().getEmployeeNumber();

            List<Orders> orders =
                    ordersRepository.findByCustomerCustomerNumber(c.getCustomerNumber());

            double total = 0;
            for (Orders o : orders) {
                total += getOrderValue(o.getOrderNumber());
            }

            map.merge(empId, total, Double::sum);
        }

        return convertMap(map);
    }

    // 5. MONTHLY REVENUE
    @Override
    public List<Map<String, Object>> getMonthlyRevenue() {

        List<Orders> orders = ordersRepository.findAll();
        Map<String, Double> map = new HashMap<>();

        for (Orders o : orders) {

            if (o.getOrderDate() == null) continue;

            String month = o.getOrderDate().getMonth().toString();

            double value = getOrderValue(o.getOrderNumber());

            map.merge(month, value, Double::sum);
        }

        return convertMap(map);
    }

    // 6. HIGH RISK CUSTOMERS
    @Override
    public List<Map<String, Object>> getHighRiskCustomers() {

        List<Map<String, Object>> exposure = getCustomerExposure();

        return exposure.stream()
                .filter(c -> {
                    Double total = ((Number) c.get("totalOrderValue")).doubleValue();
                    Double credit = ((Number) c.get("creditLimit")).doubleValue();
                    return total >= credit;
                })
                .toList();
    }

    // 🔧 HELPER
    private List<Map<String, Object>> convertMap(Map<?, Double> map) {

        List<Map<String, Object>> result = new ArrayList<>();

        for (Map.Entry<?, Double> entry : map.entrySet()) {
            Map<String, Object> data = new HashMap<>();
            data.put("key", entry.getKey());
            data.put("value", entry.getValue());
            result.add(data);
        }

        return result;
    }
}