package com.classicbusinessmodel_schema.backend.module.order.repository;

import com.classicbusinessmodel_schema.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
}
