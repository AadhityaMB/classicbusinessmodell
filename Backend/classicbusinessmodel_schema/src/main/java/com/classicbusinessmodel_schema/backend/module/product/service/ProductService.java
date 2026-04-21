package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction);

    ProductResponse getProductById(String productCode);

    List<ProductResponse> getProductsByLine(String productLine);

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(String productCode, UpdateProductRequest request);

    void deleteProduct(String productCode);
}