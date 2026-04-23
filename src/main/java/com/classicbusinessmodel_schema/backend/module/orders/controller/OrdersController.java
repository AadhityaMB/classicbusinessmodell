package com.classicbusinessmodel_schema.backend.module.orders.controller;

import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.service.OrdersService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    @PostMapping("/orders")
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }
    @GetMapping("/orders")
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/orders/{orderNumber}")
    public OrderResponseDTO getOrder(@PathVariable Integer orderNumber) {
        return orderService.getOrderById(orderNumber);
    }
    @PutMapping("/orders/{orderNumber}")
    public OrderResponseDTO updateOrder(@PathVariable Integer orderNumber,
                                        @Valid @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(orderNumber, request);
    }
    @PatchMapping("/orders/{orderNumber}/status")
    public OrderResponseDTO updateStatus(@PathVariable Integer orderNumber,
                                         @RequestBody OrderRequestDTO request) {
        return orderService.updateOrderStatus(orderNumber, request);
    }

    @GetMapping("/customers/{customerNumber}/orders")
    public List<OrderResponseDTO> getOrdersByCustomer(@PathVariable Integer customerNumber) {
        return orderService.getOrdersByCustomer(customerNumber);
    }

    @GetMapping("/orders/search")
    public List<OrderResponseDTO> searchOrders(
            @RequestParam String status,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {
        return orderService.searchOrders(status, fromDate, toDate);
    }
}
