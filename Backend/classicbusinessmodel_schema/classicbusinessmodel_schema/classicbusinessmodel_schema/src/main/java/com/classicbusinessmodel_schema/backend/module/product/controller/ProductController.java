package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Create new product")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        ProductResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        201,
                        "Product created successfully",
                        response
                ));
    }

    @PutMapping("/{productCode}")
    @Operation(summary = "Update product")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable("productCode") String productCode,
            @Valid @RequestBody UpdateProductRequest request) {

        ProductResponse response = productService.updateProduct(productCode, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete product")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable("productCode") String productCode) {

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
    @Operation(summary = "Get all products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {

        List<ProductResponse> response = productService.getAllProducts();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Products fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/{productCode}")
    @Operation(summary = "Get product by product code")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable String productCode) {

        ProductResponse response = productService.getProductById(productCode);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product fetched successfully",
                        response
                )
        );
    }
}