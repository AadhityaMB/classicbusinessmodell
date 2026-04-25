package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import java.util.List;

// Service interface for managing product operations
public interface ProductService {

    // Fetch paginated products with sorting
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction);

    // Fetch a product by its product code
    ProductResponse getProductById(String productCode);

    // Create a new product
    ProductResponse createProduct(CreateProductRequest request);

    // Update an existing product
    ProductResponse updateProduct(String productCode, UpdateProductRequest request);

    // Delete a product by product code
    void deleteProduct(String productCode);
}