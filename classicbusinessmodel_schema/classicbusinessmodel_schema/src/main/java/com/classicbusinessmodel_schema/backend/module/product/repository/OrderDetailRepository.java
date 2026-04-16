package com.classicbusinessmodel_schema.backend.module.product.repository;


import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, OrderDetailsId> {

    // Get items for an order
    List<OrderDetails> findByOrderOrderNumber(Integer orderNumber);

    // Get by product
    List<OrderDetails> findByProductProductCode(String productCode);

    //Custom queries
    // 1. Find items with quantity greater than (bulk orders)
    List<OrderDetails> findByQuantityOrderedGreaterThan(Integer quantity);

    // 2. Find items with price greater than
    List<OrderDetails> findByPriceEachGreaterThan(BigDecimal price);

    // 3. Find items by order + product (composite filtering)
    List<OrderDetails> findByOrderOrderNumberAndProductProductCode(Integer orderNumber, String productCode);
}
