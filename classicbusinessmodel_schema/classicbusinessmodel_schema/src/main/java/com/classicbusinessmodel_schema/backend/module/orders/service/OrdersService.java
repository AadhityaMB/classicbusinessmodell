package com.classicbusinessmodel_schema.backend.module.orders.service;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrdersService {

    OrderResponseDTO createOrder(OrderRequestDTO request);

    Page<OrderResponseDTO> getAllOrders(Pageable pageable);

    OrderResponseDTO getOrderById(Integer orderNumber);

    OrderResponseDTO updateOrder(Integer orderNumber, OrderRequestDTO request);

    OrderResponseDTO updateOrderStatus(Integer orderNumber, OrderRequestDTO request);

    List<OrderResponseDTO> getOrdersByCustomer(Integer customerNumber);

    List<OrderResponseDTO> searchOrders(String status, LocalDate fromDate, LocalDate toDate);
}
