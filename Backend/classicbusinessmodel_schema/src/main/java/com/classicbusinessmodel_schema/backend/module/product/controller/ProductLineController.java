package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Handles APIs for managing product lines and related products
@RestController
@RequestMapping("/api/product-lines")
@Tag(name = "Product Lines", description = "Product category APIs")
public class ProductLineController {

    // Service layer dependency for product line operations
    @Autowired
    private ProductLineService productLineService;

    // Fetch all product lines
    @GetMapping
    @Operation(summary = "Get all product lines", description = "Fetches all product categories with pagination and sorting")
    public ResponseEntity<ApiResponse<Page<ProductLineResponse>>> getAllProductLines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productLine") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<ProductLineResponse> response =
                productLineService.getAllProductLines(page, size, sortBy, direction);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product lines fetched successfully",
                        response
                )
        );
    }

    // Fetch details of a specific product line
    @GetMapping("/{productLine}")
    @Operation(summary = "Get product line by name", description = "Fetches details of a specific product line")
    public ResponseEntity<ApiResponse<ProductLineResponse>> getProductLine(
            @PathVariable String productLine) {

        ProductLineResponse response =
                productLineService.getProductLineById(productLine);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Product line fetched successfully",
                        response
                )
        );
    }

    // Fetch all products belonging to a specific product line
    @GetMapping("/{productLine}/products")
    @Operation(summary = "Get products under a product line", description = "Fetches all products belonging to a specific product line")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByLine(
            @PathVariable String productLine) {

        List<ProductResponse> response =
                productLineService.getProductsByLine(productLine);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Products fetched for product line",
                        response
                )
        );
    }
}