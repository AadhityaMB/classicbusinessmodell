package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repository for handling product line data operations
@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, String> {

    // CUSTOM QUERY
    // Check if product line exists
    @Query("SELECT COUNT(pl) > 0 FROM ProductLine pl WHERE pl.productLine = :productLine")
    boolean existsProductLine(@Param("productLine") String productLine);
}