package com.classicbusinessmodel_schema.backend.module.product.repository;


import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, OrderDetailsId> {

    // Get items for an order
    List<OrderDetails> findByOrderOrderNumber(Integer orderNumber);

    // Get by product
    List<OrderDetails> findByProductProductCode(String productCode);
}
