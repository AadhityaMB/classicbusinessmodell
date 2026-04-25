package com.classicbusinessmodel_schema.backend.module.orders.service;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrdersService {

    // Create a new order
    OrderResponseDTO createOrder(OrderRequestDTO request);

    // Get all orders with pagination
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);

    // Get order by order number
    OrderResponseDTO getOrderById(Integer orderNumber);

    // Update complete order details
    OrderResponseDTO updateOrder(Integer orderNumber, OrderRequestDTO request);

    // Update only order status
    OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderRequestDTO request);

    // Get all orders for a specific customer
    List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber);

    // Search orders using status and date range
    List<OrderResponseDTO> searchOrders(String status, LocalDate fromDate, LocalDate toDate);
}
