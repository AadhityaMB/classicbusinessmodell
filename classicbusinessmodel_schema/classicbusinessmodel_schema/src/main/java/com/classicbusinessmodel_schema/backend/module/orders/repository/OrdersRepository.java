package com.classicbusinessmodel_schema.backend.module.orders.repository;

import com.classicbusinessmodel_schema.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
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
}