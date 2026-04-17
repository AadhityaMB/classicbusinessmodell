package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductLineService;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-lines")
@Tag(name = "Product Lines", description = "Product category APIs")
public class ProductLineController {

    private final ProductLineService productLineService;
    private final ProductService productService;

    // Manual constructor injection (replaces @RequiredArgsConstructor)
    public ProductLineController(ProductLineService productLineService,
                                 ProductService productService) {
        this.productLineService = productLineService;
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all product lines")
    public ResponseEntity<List<ProductLineResponse>> getAllProductLines() {
        return ResponseEntity.ok(productLineService.getAllProductLines());
    }

    @GetMapping("/{productLine}")
    @Operation(summary = "Get product line by name")
    public ResponseEntity<ProductLineResponse> getProductLine(
            @PathVariable("productLine") String productLine) {

        return ResponseEntity.ok(
                productLineService.getProductLineById(productLine)
        );
    }

    @GetMapping("/{productLine}/products")
    @Operation(summary = "Get products under a product line")
    public ResponseEntity<List<ProductResponse>> getProductsByLine(
            @PathVariable("productLine") String productLine) {

        return ResponseEntity.ok(
                productService.getProductsByLine(productLine)
        );
    }
}