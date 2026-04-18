package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, String> {

    //Custom queries
    // Search by text description
    @Query("SELECT pl FROM ProductLine pl WHERE LOWER(pl.textDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductLine> findByTextDescriptionContaining(@Param("keyword") String keyword);

    // Search by HTML description
    @Query("SELECT p FROM ProductLine p WHERE p.htmlDescription LIKE %:keyword%")
    List<ProductLine> searchHtml(@Param("keyword") String keyword);
    // Check existence
    @Query("SELECT COUNT(pl) > 0 FROM ProductLine pl WHERE pl.productLine = :productLine")
    boolean existsByProductLine(@Param("productLine") String productLine);
}