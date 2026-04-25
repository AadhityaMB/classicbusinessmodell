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

    // Get orders by customer
    List<Orders> findByCustomerCustomerNumber(Integer customerNumber);

    // Find by status
    List<Orders> findByStatus(String status);

    // Date range search
    List<Orders> findByOrderDateBetween(LocalDate start, LocalDate end);

    List<Orders> findByStatusAndOrderDateBetween(String status, LocalDate fromDate, LocalDate toDate);

    // 1. Custom JPQL - Get orders with customer name
    @Query("SELECT o FROM Orders o WHERE o.customer.customerName = :name")
    List<Orders> findByCustomerName(@Param("name") String customerName);

    // 2. Native Query - Get shipped orders
    @Query(value = "SELECT * FROM orders WHERE status = 'Shipped'", nativeQuery = true)
    List<Orders> getAllShippedOrders();

    // 3. Orders not shipped yet
    @Query("SELECT o FROM Orders o WHERE o.shippedDate IS NULL")
    List<Orders> findUnshippedOrders();

    // 4. Orders after a specific date
    @Query("SELECT o FROM Orders o WHERE o.orderDate > :date")
    List<Orders> findOrdersAfterDate(@Param("date") LocalDate date);

    // 5. Count orders by status
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = :status")
    long countByStatus(@Param("status") String status);
}