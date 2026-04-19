package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/{orderNumber}/items")
@Tag(name = "Order Details", description = "Order line items APIs")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    @Operation(summary = "Get all items for a specific order")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> getOrderItems(
            @PathVariable Integer orderNumber) {

        List<OrderDetailResponse> response = orderDetailService.getItemsByOrder(orderNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order items fetched successfully",
                        response
                )
        );
    }

    @PostMapping
    @Operation(summary = "Add product to an order")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> addItem(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderDetailRequest request) {

        request.setOrderNumber(orderNumber);

        OrderDetailResponse response = orderDetailService.addItem(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                201,
                                "Item added to order successfully",
                                response
                        )
                );
    }

    @PutMapping("/{productCode}")
    @Operation(summary = "Update order item details")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> updateItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode,
            @Valid @RequestBody OrderDetailRequest request) {

        OrderDetailResponse response =
                orderDetailService.updateItem(orderNumber, productCode, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order item updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete item from an order")
    public ResponseEntity<ApiResponse<Void>> deleteItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode) {

        orderDetailService.deleteItem(orderNumber, productCode);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order item deleted successfully",
                        null
                )
        );
    }
}