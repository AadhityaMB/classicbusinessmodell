package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repository for handling order item data operations
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, OrderDetailsId> {

    // Fetch paginated items for a specific order
    Page<OrderDetails> findByOrder_OrderNumber(Integer orderNumber, Pageable pageable);

    // CUSTOM QUERIES

    // Fetch a specific order item using order number and product code
    @Query("SELECT od FROM OrderDetails od WHERE od.order.orderNumber = :orderNumber AND od.product.productCode = :productCode")
    List<OrderDetails> findByOrderOrderNumberAndProductProductCode(
            @Param("orderNumber") Integer orderNumber,
            @Param("productCode") String productCode
    );

    // Check if an order item exists for given order and product
    @Query("SELECT COUNT(od) > 0 FROM OrderDetails od WHERE od.order.orderNumber = :orderNumber AND od.product.productCode = :productCode")
    boolean existsByOrderAndProduct(
            @Param("orderNumber") Integer orderNumber,
            @Param("productCode") String productCode
    );
}