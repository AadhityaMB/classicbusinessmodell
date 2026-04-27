package com.classicbusinessmodel_schema.backend.module.orders.service;

import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.exception.InvalidDataException;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
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

    // Create a new order and save it to database
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Orders order = new Orders();

        Integer orderNumber = (int) (Math.random() * 100000);

        if (orderRepository.existsById(orderNumber)) {
            throw new ResourceAlreadyExistsException("Order already exists");
        }

        order.setOrderNumber(orderNumber);

        order.setCustomer(customerRepository.findById(request.getCustomerNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found")));

        order.setOrderDate(request.getOrderDate());
        order.setRequiredDate(request.getRequiredDate());
        order.setShippedDate(request.getShippedDate());
        order.setStatus(request.getStatus());
        order.setComments(request.getComments());

        return mapToDTO(orderRepository.save(order));
    }

    // Fetch all orders with pagination support
    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // Get a single order using order number
    @Override
    public OrderResponseDTO getOrderById(Integer orderNumber) {
        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return mapToDTO(order);
    }

    // Update all fields of an existing order
    @Override
    public OrderResponseDTO updateOrder(Integer orderNumber, OrderRequestDTO request) {

        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setOrderDate(request.getOrderDate());
        order.setRequiredDate(request.getRequiredDate());
        order.setShippedDate(request.getShippedDate());
        order.setStatus(request.getStatus());
        order.setComments(request.getComments());

        return mapToDTO(orderRepository.save(order));
    }

    // Update only the status field of an order
    @Override
    public OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderRequestDTO request) {

        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        return mapToDTO(orderRepository.save(order));
    }

    // Fetch all orders belonging to a specific customer
    @Override
    public List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber) {
        return orderRepository.findByCustomerCustomerNumber(customerNumber)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Search orders based on status and date range
    @Override
    public List<OrderResponseDTO> searchOrders(String status, LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new InvalidDataException("From Date cannot be greater than To Date");
        }
        return orderRepository.findByStatusAndOrderDateBetween(status, fromDate, toDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Convert Orders entity to OrderResponseDTO
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