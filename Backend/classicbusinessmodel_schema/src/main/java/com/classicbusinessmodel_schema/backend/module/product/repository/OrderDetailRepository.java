package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, OrderDetailsId> {

    Page<OrderDetails> findByOrder_OrderNumber(Integer orderNumber, Pageable pageable);

    List<OrderDetails> findByOrderOrderNumber(Integer orderNumber);

    // Get all items for a specific product
    @Query("SELECT od FROM OrderDetails od WHERE od.product.productCode = :productCode")
    List<OrderDetails> findByProductProductCode(@Param("productCode") String productCode);

    // CUSTOM QUERIES

    // Bulk orders (quantity greater than)
    @Query("SELECT od FROM OrderDetails od WHERE od.quantityOrdered > :quantity")
    List<OrderDetails> findByQuantityOrderedGreaterThan(@Param("quantity") Integer quantity);

    // High price items
    @Query("SELECT od FROM OrderDetails od WHERE od.priceEach > :price")
    List<OrderDetails> findByPriceEachGreaterThan(@Param("price") BigDecimal price);

    // Composite filter (order + product)
    @Query("SELECT od FROM OrderDetails od WHERE od.order.orderNumber = :orderNumber AND od.product.productCode = :productCode")
    List<OrderDetails> findByOrderOrderNumberAndProductProductCode(
            @Param("orderNumber") Integer orderNumber,
            @Param("productCode") String productCode
    );

    // EXISTENCE CHECK (important for service validation)
    @Query("SELECT COUNT(od) > 0 FROM OrderDetails od WHERE od.order.orderNumber = :orderNumber AND od.product.productCode = :productCode")
    boolean existsByOrderAndProduct(
            @Param("orderNumber") Integer orderNumber,
            @Param("productCode") String productCode
    );
}