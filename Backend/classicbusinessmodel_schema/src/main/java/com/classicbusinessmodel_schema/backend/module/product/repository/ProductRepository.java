package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

// Repository for handling product data operations
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Fetch products belonging to a specific product line
    @Query("SELECT p FROM Product p WHERE p.productLine.productLine = :productLine")
    List<Product> findByProductLineProductLine(@Param("productLine") String productLine);

    // CUSTOM QUERY
    // Check if product exists using custom query
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.productCode = :productCode")
    boolean existsProduct(@Param("productCode") String productCode);
}