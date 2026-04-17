package com.classicbusinessmodel_schema.backend.module.orders.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
//import com.classicbusinessmodel_schema.backend.module.orders.dto.*;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderDetailsRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderDetailsResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    // ✅ CREATE ORDER
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Orders order = new Orders();

        order.setCustomer(customerRepository.findById(request.getCustomerNumber())
                .orElseThrow(() -> new RuntimeException("Customer not found")));

        order.setOrderDate(request.getOrderDate());
        order.setRequiredDate(request.getRequiredDate());
        order.setShippedDate(request.getShippedDate());
        order.setStatus(request.getStatus());
        order.setComments(request.getComments());

        // 🔹 Handle OrderDetails
        List<OrderDetails> detailsList = new ArrayList<>();

        if (request.getOrderDetails() != null) {
            for (OrderDetailsRequestDTO dto : request.getOrderDetails()) {

                OrderDetails details = new OrderDetails();

                Product product = productRepository.findById(dto.getProductCode())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                details.setProduct(product);
                details.setQuantityOrdered(dto.getQuantityOrdered());
                details.setPriceEach(dto.getPriceEach());
                details.setOrderLineNumber(dto.getOrderLineNumber());

                details.setOrder(order); // link both sides
                detailsList.add(details);
            }
        }

        order.setOrderDetails(detailsList);

        return mapToDTO(orderRepository.save(order));
    }

    // ✅ GET ALL
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @Override
    public OrderResponseDTO getOrderById(Integer orderNumber) {
        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToDTO(order);
    }

    // ✅ UPDATE ORDER
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

    // ✅ UPDATE STATUS
    @Override
    public OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderRequestDTO request) {

        Orders order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        return mapToDTO(orderRepository.save(order));
    }

    // ✅ GET ORDERS BY CUSTOMER
    @Override
    public List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber) {

        return orderRepository.findByCustomerCustomerNumber(customerNumber)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ SEARCH ORDERS
    @Override
    public List<OrderResponseDTO> searchOrders(String status, LocalDate fromDate, LocalDate toDate) {

        return orderRepository
                .findByStatusAndOrderDateBetween(status, fromDate, toDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ ENTITY → DTO MAPPING
    private OrderResponseDTO mapToDTO(Orders order) {

        List<OrderDetailsResponseDTO> detailsDTO = new ArrayList<>();

        if (order.getOrderDetails() != null) {
            detailsDTO = order.getOrderDetails().stream()
                    .map(d -> new OrderDetailsResponseDTO(
                            d.getProduct().getProductCode(),
                            d.getQuantityOrdered(),
                            d.getPriceEach(),
                            d.getOrderLineNumber()
                    ))
                    .collect(Collectors.toList());
        }

        return new OrderResponseDTO(
                order.getOrderNumber(),
                order.getCustomer().getCustomerNumber(),
                order.getOrderDate(),
                order.getRequiredDate(),
                order.getShippedDate(),
                order.getStatus(),
                order.getComments(),
                detailsDTO
        );
    }
}