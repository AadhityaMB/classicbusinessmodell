package com.classicbusinessmodel_schema.backend.module.orders.service;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository,
                             CustomerRepository customerRepository) {
        this.ordersRepository = ordersRepository;
        this.customerRepository = customerRepository;
    }

    // CREATE
    @Override
    public Orders createOrder(Orders order) {

        if (order.getCustomer() == null) {
            throw new IllegalArgumentException("Customer is required");
        }

        Integer customerId = order.getCustomer().getCustomerNumber();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        return ordersRepository.save(order);
    }

    // READ ALL
    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    // READ BY ID
    @Override
    public Orders getOrderById(Integer orderNumber) {
        return ordersRepository.findById(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    // UPDATE
    @Override
    public Orders updateOrder(Integer orderNumber, Orders order) {

        Orders existing = getOrderById(orderNumber);

        existing.setRequiredDate(order.getRequiredDate());
        existing.setShippedDate(order.getShippedDate());
        existing.setComments(order.getComments());

        return ordersRepository.save(existing);
    }

    // UPDATE STATUS (PATCH)
    @Override
    public Orders updateOrderStatus(Integer orderNumber, String status) {

        Orders order = getOrderById(orderNumber);

        order.setStatus(status);

        return ordersRepository.save(order);
    }

    // GET ORDERS BY CUSTOMER
    @Override
    public List<Orders> getOrdersByCustomer(Integer customerNumber) {
        return ordersRepository.findByCustomerCustomerNumber(customerNumber);
    }

    // SEARCH ORDERS
    @Override
    public List<Orders> searchOrders(String status, LocalDate fromDate, LocalDate toDate) {

        if (status != null && fromDate != null && toDate != null) {
            return ordersRepository.findByStatus(status)
                    .stream()
                    .filter(o -> !o.getOrderDate().isBefore(fromDate)
                            && !o.getOrderDate().isAfter(toDate))
                    .toList();
        }

        if (status != null) {
            return ordersRepository.findByStatus(status);
        }

        if (fromDate != null && toDate != null) {
            return ordersRepository.findByOrderDateBetween(fromDate, toDate);
        }

        return ordersRepository.findAll();
    }
}
