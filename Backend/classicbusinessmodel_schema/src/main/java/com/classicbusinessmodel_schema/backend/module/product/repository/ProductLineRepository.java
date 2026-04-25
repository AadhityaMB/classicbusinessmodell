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

    //Custom queries

    // Fetch product lines matching a keyword in text description (case-insensitive)
    @Query("SELECT pl FROM ProductLine pl WHERE LOWER(pl.textDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductLine> findByTextDescriptionContainingIgnoreCase(@Param("keyword") String keyword);

    // Check if a product line exists by name
    @Query("SELECT COUNT(pl) > 0 FROM ProductLine pl WHERE pl.productLine = :productLine")
    boolean existsByProductLine(@Param("productLine") String productLine);
}