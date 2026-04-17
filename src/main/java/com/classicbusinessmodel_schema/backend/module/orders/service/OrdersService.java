package com.classicbusinessmodel_schema.backend.module.orders.service;

import com.classicbusinessmodel_schema.backend.entity.Orders;

import java.time.LocalDate;
import java.util.List;

public interface OrdersService {

    Orders createOrder(Orders order);

    List<Orders> getAllOrders();

    Orders getOrderById(Integer orderNumber);

    Orders updateOrder(Integer orderNumber, Orders order);

    Orders updateOrderStatus(Integer orderNumber, String status);

    List<Orders> getOrdersByCustomer(Integer customerNumber);

    List<Orders> searchOrders(String status, LocalDate fromDate, LocalDate toDate);
}
