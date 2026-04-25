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
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders/{orderNumber}/items")
@Tag(name = "Order Details", description = "Order line items APIs")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    // GET ALL ITEMS FOR ORDER
    @GetMapping
    @Operation(summary = "Get all items for a specific order", description = "Fetches all products associated with a given order number")
    public ResponseEntity<ApiResponse<Page<OrderDetailResponse>>> getOrderItems(
            @PathVariable Integer orderNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderLineNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<OrderDetailResponse> response =
                orderDetailService.getItemsByOrder(orderNumber, page, size, sortBy, direction);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order items fetched successfully",
                        response
                )
        );
    }

    // GET SINGLE ORDER ITEM
    @GetMapping("/{productCode}")
    @Operation(summary = "Get specific order item", description = "Fetches details of a specific product within an order")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getItem(
            @PathVariable Integer orderNumber,
            @PathVariable String productCode) {

        OrderDetailResponse response =
                orderDetailService.getItem(orderNumber, productCode);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order item fetched successfully",
                        response
                )
        );
    }

    // ADD ITEM TO ORDER
    @PostMapping
    @Operation(summary = "Add product to an order", description = "Adds a new product item to a specific order")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> addItem(
            @PathVariable Integer orderNumber,
            @Valid @RequestBody OrderDetailRequest request) {

        request.setOrderNumber(orderNumber);

        OrderDetailResponse response =
                orderDetailService.addItem(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                201,
                                "Item added to order successfully",
                                response
                        )
                );
    }

    // UPDATE ORDER ITEM
    @PutMapping("/{productCode}")
    @Operation(summary = "Update order item details", description = "Updates quantity or price of a product in an order")
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

    // DELETE ORDER ITEM
    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete item from an order", description = "Removes a product from a specific order")
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