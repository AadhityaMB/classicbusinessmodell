package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}