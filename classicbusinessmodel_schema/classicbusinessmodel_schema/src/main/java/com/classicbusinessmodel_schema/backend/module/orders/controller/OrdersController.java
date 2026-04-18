package com.classicbusinessmodel_schema.backend.module.orders.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.service.OrdersService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrdersService orderService;

    //  CREATE ORDER
    @PostMapping("/orders")
    public ApiResponse<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request) {

        OrderResponseDTO response = orderService.createOrder(request);

        return new ApiResponse<>(
                LocalDateTime.now(),
                201,
                "Order created successfully",
                response
        );
    }

    //  GET ALL ORDERS
    @GetMapping("/orders")
    public ApiResponse<List<OrderResponseDTO>> getAllOrders() {

        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Orders fetched successfully",
                orderService.getAllOrders()
        );
    }

    // GET ORDER BY ID
    @GetMapping("/orders/{orderNumber}")
    public ApiResponse<OrderResponseDTO> getOrder(@PathVariable Integer orderNumber) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Order fetched successfully",
                orderService.getOrderById(orderNumber)
        );
    }

    // UPDATE FULL ORDER
    @PutMapping("/orders/{orderNumber}")
    public ApiResponse<OrderResponseDTO> updateOrder(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderRequestDTO request) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Order updated successfully",
                orderService.updateOrder(orderNumber, request)
        );
    }

    // UPDATE STATUS ONLY
    @PatchMapping("/orders/{orderNumber}/status")
    public ApiResponse<OrderResponseDTO> updateStatus(
            @PathVariable Integer orderNumber,
            @RequestBody OrderRequestDTO request) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Order status updated successfully",
                orderService.updateOrderStatus(orderNumber, request)
        );
    }

    //  GET ORDERS BY CUSTOMER
    @GetMapping("/customers/{customerNumber}/orders")
    public ApiResponse<List<OrderResponseDTO>> getOrdersByCustomer(
            @PathVariable Integer customerNumber) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Customer orders fetched successfully",
                orderService.getOrdersByCustomer(customerNumber)
        );
    }

    // SEARCH ORDERS
    @GetMapping("/orders/search")
    public ApiResponse<List<OrderResponseDTO>> searchOrders(
            @RequestParam String status,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Filtered orders fetched successfully",
                orderService.searchOrders(status, fromDate, toDate)
        );
    }
}