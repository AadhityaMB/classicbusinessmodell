package com.classicbusinessmodel_schema.backend.module.product.controller;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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

    private final ProductService productService;

    // Manual constructor injection (replaces @RequiredArgsConstructor)
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create new product")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    @PutMapping("/{productCode}")
    @Operation(summary = "Update product")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("productCode") String productCode,
            @Valid @RequestBody UpdateProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(productCode, request)
        );
    }

    @DeleteMapping("/{productCode}")
    @Operation(summary = "Delete product")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("productCode") String productCode) {

        productService.deleteProduct(productCode);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productCode}")
    @Operation(summary = "Get product by product code")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable String productCode) {

        return ResponseEntity.ok(
                productService.getProductById(productCode)
        );
    }
}