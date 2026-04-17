package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/{orderNumber}/items")
@Tag(name = "Order Details", description = "Order line items APIs")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    // Manual Constructor Injection (replaces @RequiredArgsConstructor)
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    @Operation(summary = "Get all items for a specific order")
    public ResponseEntity<List<OrderDetailResponse>> getOrderItems(
            @PathVariable Integer orderNumber) {

        return ResponseEntity.ok(
                orderDetailService.getItemsByOrder(orderNumber)
        );
    }

    @PostMapping
    @Operation(summary = "Add product to an order")
    public ResponseEntity<OrderDetailResponse> addItem(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderDetailRequest request) {

        request.setOrderNumber(orderNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDetailService.addItem(request));
    }

    @PutMapping("/{productCode}")
    @Operation(summary = "Update order item details")
    public ResponseEntity<OrderDetailResponse> updateItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode,
            @Valid @RequestBody OrderDetailRequest request) {

        return ResponseEntity.ok(
                orderDetailService.updateItem(orderNumber, productCode, request)
        );
    }

    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete item from an order")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode) {

        orderDetailService.deleteItem(orderNumber, productCode);

        return ResponseEntity.noContent().build();
    }
}