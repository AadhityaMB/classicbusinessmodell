package com.classicbusinessmodel_schema.backend.module.orders.service;

import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Orders order = new Orders();
        order.setOrderNumber((int) (Math.random() * 100000));

        order.setCustomer(customerRepository.findById(request.getCustomerNumber())
                .orElseThrow(() -> new RuntimeException("Customer not found")));

        order.setOrderDate(request.getOrderDate());
        order.setRequiredDate(request.getRequiredDate());
        order.setShippedDate(request.getShippedDate());
        order.setStatus(request.getStatus());
        order.setComments(request.getComments());

        return mapToDTO(orderRepository.save(order));
    }

    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    public OrderResponseDTO getOrderById(Integer orderNumber) {
        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToDTO(order);
    }

    @Override
    public OrderResponseDTO updateOrder(Integer orderNumber, OrderRequestDTO request) {

        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderDate(request.getOrderDate());
        order.setRequiredDate(request.getRequiredDate());
        order.setShippedDate(request.getShippedDate());
        order.setStatus(request.getStatus());
        order.setComments(request.getComments());

        return mapToDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderRequestDTO request) {

        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        return mapToDTO(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber) {
        return orderRepository.findByCustomerCustomerNumber(customerNumber)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> searchOrders(String status, LocalDate fromDate, LocalDate toDate) {
        return orderRepository.findByStatusAndOrderDateBetween(status, fromDate, toDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToDTO(Orders order) {
        return new OrderResponseDTO(
                order.getOrderNumber(),
                order.getCustomer().getCustomerNumber(),
                order.getOrderDate(),
                order.getRequiredDate(),
                order.getShippedDate(),
                order.getStatus(),
                order.getComments()
        );
    }
}