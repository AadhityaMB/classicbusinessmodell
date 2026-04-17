package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductLineServiceImpl implements ProductLineService {

    private final ProductLineRepository repository;

    public ProductLineServiceImpl(ProductLineRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductLine> getAllProductLines() {
        return repository.findAll();
    }

    @Override
    public ProductLine getProductLineById(String productLine) {
        return repository.findById(productLine)
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));
    }
}
