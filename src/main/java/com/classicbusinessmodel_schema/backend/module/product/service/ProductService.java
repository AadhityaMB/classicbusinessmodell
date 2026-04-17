package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.Product;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(String productCode);

    List<Product> getProductsByLine(String productLine);
}