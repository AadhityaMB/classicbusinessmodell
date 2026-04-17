package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import java.util.List;

public interface ProductLineService {

    List<ProductLine> getAllProductLines();

    ProductLine getProductLineById(String productLine);
}
