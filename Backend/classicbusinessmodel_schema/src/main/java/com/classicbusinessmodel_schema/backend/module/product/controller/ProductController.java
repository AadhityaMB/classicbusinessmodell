package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

// Handles APIs for managing products (create, update, delete, fetch)
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    // Service layer dependency for product operations
    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping
    @Operation(summary = "Create new product", description = "Adds a new product to the system")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        ProductResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                201,
                                "Product created successfully",
                                response
                        )
                );
    }

    // Update an existing product by product code
    @PutMapping("/{productCode}")
    @Operation(summary = "Update product", description = "Updates details of an existing product")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable String productCode,
            @Valid @RequestBody UpdateProductRequest request) {

        ProductResponse response =
                productService.updateProduct(productCode, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product updated successfully",
                        response
                )
        );
    }

    // Delete a product by product code
    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete product", description = "Removes a product from the system")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable String productCode) {

        // Delegate delete operation to service layer
        productService.deleteProduct(productCode);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product deleted successfully",
                        null
                )
        );
    }

    // Fetch all products with pagination and sorting
    @GetMapping
    @Operation(summary = "Get all products", description = "Fetches all products with pagination and sorting")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<ProductResponse> response =
                productService.getAllProducts(page, size, sortBy, direction);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Products fetched successfully",
                        response
                )
        );
    }

    // Fetch a product using its product code
    @GetMapping("/{productCode}")
    @Operation(summary = "Get product by product code", description = "Fetches details of a specific product using its code")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable String productCode) {

        ProductResponse response =
                productService.getProductById(productCode);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product fetched successfully",
                        response
                )
        );
    }
}