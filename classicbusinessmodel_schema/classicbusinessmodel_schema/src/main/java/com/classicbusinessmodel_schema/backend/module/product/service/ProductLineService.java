package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.ProductLineRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import java.util.List;

public interface ProductLineService {

    List<ProductLineResponse> getAllProductLines();

    ProductLineResponse getProductLineById(String productLine);

    List<ProductResponse> getProductsByLine(String productLine);

    ProductLineResponse createProductLine(ProductLineRequest request);

    ProductLineResponse updateProductLine(String productLine, ProductLineRequest request);

    void deleteProductLine(String productLine);
}