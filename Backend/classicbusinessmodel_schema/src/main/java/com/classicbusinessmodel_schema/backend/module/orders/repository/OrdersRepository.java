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

    // Get all orders for a specific customer
    List<Orders> findByCustomerCustomerNumber(Integer customerNumber);

    // Get orders by status within a date range
    List<Orders> findByStatusAndOrderDateBetween(String status, LocalDate fromDate, LocalDate toDate);

    // Custom JPQL query to fetch orders using customer name
    @Query("SELECT o FROM Orders o WHERE o.customer.customerName = :name")
    List<Orders> findByCustomerName(@Param("name") String customerName);

    // Native query to fetch all shipped orders
    @Query(value = "SELECT * FROM orders WHERE status = 'Shipped'", nativeQuery = true)
    List<Orders> getAllShippedOrders();
}