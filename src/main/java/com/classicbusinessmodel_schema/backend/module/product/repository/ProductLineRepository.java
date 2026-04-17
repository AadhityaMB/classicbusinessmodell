package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, String> {

    //Custom queries
    // 1. Search by text description
    List<ProductLine> findByTextDescriptionContainingIgnoreCase(String keyword);

    // 2. Search by HTML description
    List<ProductLine> findByHtmlDescriptionContainingIgnoreCase(String keyword);

    // 3. Check existence
    boolean existsByProductLine(String productLine);
}