package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // 1. Get products by product line
    @Query("SELECT p FROM Product p WHERE p.productLine.productLine = :productLine")
    List<Product> findByProductLineProductLine(@Param("productLine") String productLine);

    // 2. Price filter
    @Query("SELECT p FROM Product p WHERE p.buyPrice > :price")
    List<Product> findByBuyPriceGreaterThan(@Param("price") BigDecimal price);

    // CUSTOM QUERIES

    // Find by vendor
    @Query("SELECT p FROM Product p WHERE p.productVendor = :productVendor")
    List<Product> findByProductVendor(@Param("productVendor") String productVendor);

    // Low stock products
    @Query("SELECT p FROM Product p WHERE p.quantityInStock < :quantity")
    List<Product> findByQuantityInStockLessThan(@Param("quantity") Integer quantity);

    // Search by product name (case-insensitive)
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByProductNameContainingIgnoreCase(@Param("keyword") String keyword);

    // Existence check (important for duplicate handling)
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.productCode = :productCode")
    boolean existsByProductCode(@Param("productCode") String productCode);
}