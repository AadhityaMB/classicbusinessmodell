package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductLineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/product-lines")
@Tag(name = "Product Lines", description = "Product category APIs")
public class ProductLineController {

    @Autowired
    private ProductLineService productLineService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductLineResponse>>> getAllProductLines() {

        List<ProductLineResponse> response = productLineService.getAllProductLines();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Product lines fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/{productLine}")
    public ResponseEntity<ApiResponse<ProductLineResponse>> getProductLine(
            @PathVariable String productLine) {

        ProductLineResponse response = productLineService.getProductLineById(productLine);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Product line fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/{productLine}/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByLine(
            @PathVariable String productLine) {

        List<ProductResponse> response = productLineService.getProductsByLine(productLine);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Products fetched for product line",
                        response
                )
        );
    }
}