package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Get by product line
    List<Product> findByProductLineProductLine(String productLine);

    // Price filter
    List<Product> findByBuyPriceGreaterThan(BigDecimal price);

    //Custom queries
    // 1. Find by vendor
    List<Product> findByProductVendor(String productVendor);

    // 2. Find low stock products
    List<Product> findByQuantityInStockLessThan(Integer quantity);

    // 3. Find by name containing (search)
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}