package com.classicbusinessmodel_schema.backend.module.orders.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrdersService orderService;

    // Creates a new order for an existing customer
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order for an existing customer"
    )
    @PostMapping("/orders")
    public ApiResponse<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request) {

        OrderResponseDTO response = orderService.createOrder(request);

        return new ApiResponse<>(
                201,
                "Order created successfully",
                response
        );
    }

    // Fetches all orders with pagination and sorting
    @Operation(
            summary = "Get all orders",
            description = "Fetches all orders with pagination and sorting"
    )
    @GetMapping("/orders")
    public ApiResponse<Page<OrderResponseDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderNumber") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<OrderResponseDTO> result = orderService.getAllOrders(pageable);

        return new ApiResponse<>(
                200,
                "Orders fetched successfully",
                result
        );
    }

    // Retrieves a specific order using order number
    @Operation(
            summary = "Get order by ID",
            description = "Retrieves a specific order using order number"
    )
    @GetMapping("/orders/{orderNumber}")
    public ApiResponse<OrderResponseDTO> getOrder(@PathVariable Integer orderNumber) {

        return new ApiResponse<>(
                200,
                "Order fetched successfully",
                orderService.getOrderById(orderNumber)
        );
    }

    // Updates all details of an existing order
    @Operation(
            summary = "Update an order",
            description = "Updates all details of an existing order"
    )
    @PutMapping("/orders/{orderNumber}")
    public ApiResponse<OrderResponseDTO> updateOrder(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderRequestDTO request) {

        return new ApiResponse<>(
                200,
                "Order updated successfully",
                orderService.updateOrder(orderNumber, request)
        );
    }

    // Updates only the status of an existing order
    @Operation(
            summary = "Update order status",
            description = "Updates only the status of an existing order"
    )
    @PatchMapping("/orders/{orderNumber}/status")
    public ApiResponse<OrderResponseDTO> updateStatus(
            @PathVariable Integer orderNumber,
            @RequestBody OrderRequestDTO request) {

        return new ApiResponse<>(
                200,
                "Order status updated successfully",
                orderService.updateOrderStatus(orderNumber, request)
        );
    }

    // Fetches all orders belonging to a specific customer
    @Operation(
            summary = "Get orders by customer number",
            description = "Fetches all orders belonging to a specific customer"
    )
    @GetMapping("/customers/{customerNumber}/orders")
    public ApiResponse<List<OrderResponseDTO>> getOrdersByCustomer(
            @PathVariable Integer customerNumber) {

        return new ApiResponse<>(
                200,
                "Customer orders fetched successfully",
                orderService.getOrdersByCustomer(customerNumber)
        );
    }

    // Fetches orders filtered by status and date range
    @Operation(
            summary = "Search orders",
            description = "Fetches orders filtered by status and date range"
    )
    @GetMapping("/orders/search")
    public ApiResponse<List<OrderResponseDTO>> searchOrders(
            @RequestParam String status,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        return new ApiResponse<>(
                200,
                "Filtered orders fetched successfully",
                orderService.searchOrders(status, fromDate, toDate)
        );
    }
}