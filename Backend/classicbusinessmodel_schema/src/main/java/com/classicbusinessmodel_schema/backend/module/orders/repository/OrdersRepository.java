package com.classicbusinessmodel_schema.backend.module.orders.repository;

import com.classicbusinessmodel_schema.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    // Custom query to get all orders for a specific customer
    @Query("SELECT o FROM Orders o WHERE o.customer.customerNumber = :customerNumber")
    List<Orders> findByCustomerCustomerNumber(@Param("customerNumber") Integer customerNumber);

    // Custom query to get orders by status within a date range
    @Query("SELECT o FROM Orders o WHERE o.status = :status AND o.orderDate BETWEEN :fromDate AND :toDate")
    List<Orders> findByStatusAndOrderDateBetween(
            @Param("status") String status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}

