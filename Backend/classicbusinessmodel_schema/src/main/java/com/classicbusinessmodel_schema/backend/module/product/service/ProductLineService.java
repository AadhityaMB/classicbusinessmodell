package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.ProductLineRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import java.util.List;

// Service interface for managing product lines and related products
public interface ProductLineService {

    // Fetch product lines
    Page<ProductLineResponse> getAllProductLines(int page, int size, String sortBy, String direction);

    // Fetch a specific product line by name
    ProductLineResponse getProductLineById(String productLine);

    // Fetch all products under a specific product line
    List<ProductResponse> getProductsByLine(String productLine);

    // Create a new product line
    ProductLineResponse createProductLine(ProductLineRequest request);

    // Delete a product line by name
    void deleteProductLine(String productLine);
}