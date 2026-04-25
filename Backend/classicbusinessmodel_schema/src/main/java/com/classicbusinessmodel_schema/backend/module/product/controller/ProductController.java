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

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE PRODUCT
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

    // UPDATE PRODUCT
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

    // DELETE PRODUCT
    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete product", description = "Removes a product from the system")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable String productCode) {

        productService.deleteProduct(productCode);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product deleted successfully",
                        null
                )
        );
    }

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

    // GET PRODUCT BY ID
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