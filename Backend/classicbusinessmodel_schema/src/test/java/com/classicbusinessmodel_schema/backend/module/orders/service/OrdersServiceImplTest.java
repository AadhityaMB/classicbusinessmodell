package com.classicbusinessmodel_schema.backend.module.orders.service;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto.OrderRequestDTO;
import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderResponseDTO;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    private Customer customer;
    private Orders order;
    private OrderRequestDTO request;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setCustomerNumber(101);

        order = new Orders();
        order.setOrderNumber(1);
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setRequiredDate(LocalDate.now().plusDays(5));
        order.setStatus("Shipped");

        request = new OrderRequestDTO();
        request.setCustomerNumber(101);
        request.setOrderDate(LocalDate.now());
        request.setRequiredDate(LocalDate.now().plusDays(5));
        request.setStatus("Shipped");
    }

    //  Positive: Order created successfully
    @Test
    void testCreateOrderSuccess() {
        when(customerRepository.findById(101)).thenReturn(Optional.of(customer));
        when(ordersRepository.save(any(Orders.class))).thenReturn(order);

        OrderResponseDTO response = ordersService.createOrder(request);

        assertNotNull(response);
        assertEquals("Shipped", response.getStatus());
    }

    //  Negative: Customer not found
    @Test
    void testCreateOrderCustomerNotFound() {
        when(customerRepository.findById(101)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> ordersService.createOrder(request));
    }

    //  Positive: Fetch all orders
    @Test
    void testGetAllOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Orders> page = new PageImpl<>(List.of(order));
        when(ordersRepository.findAll(pageable)).thenReturn(page);

        Page<OrderResponseDTO> result = ordersService.getAllOrders(pageable);

        assertEquals(1, result.getTotalElements());
    }

    //  Negative: No orders available
    @Test
    void testGetAllOrdersEmpty() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Orders> page = new PageImpl<>(Collections.emptyList());
        when(ordersRepository.findAll(pageable)).thenReturn(page);

        Page<OrderResponseDTO> result = ordersService.getAllOrders(pageable);

        assertTrue(result.isEmpty());
    }

    //  Positive: Get order by ID
    @Test
    void testGetOrderByIdSuccess() {
        when(ordersRepository.findById(1)).thenReturn(Optional.of(order));

        OrderResponseDTO result = ordersService.getOrderById(1);

        assertEquals(1, result.getOrderNumber());
    }

    //  Negative: Order not found
    @Test
    void testGetOrderByIdNotFound() {
        when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> ordersService.getOrderById(1));
    }

    // Positive: Update order successfully
    @Test
    void testUpdateOrderSuccess() {
        when(ordersRepository.findById(1)).thenReturn(Optional.of(order));
        when(ordersRepository.save(any())).thenReturn(order);

        OrderResponseDTO result = ordersService.updateOrder(1, request);

        assertEquals("Shipped", result.getStatus());
    }

    //  Negative: Order not found while updating
    @Test
    void testUpdateOrderNotFound() {
        when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> ordersService.updateOrder(1, request));
    }

    //  Positive: Update order status
    @Test
    void testUpdateOrderStatusSuccess() {
        when(ordersRepository.findById(1)).thenReturn(Optional.of(order));
        when(ordersRepository.save(any())).thenReturn(order);

        request.setStatus("Cancelled");

        OrderResponseDTO result = ordersService.updateOrderStatus(1, request);

        assertEquals("Cancelled", result.getStatus());
    }

    //  Positive: Search orders with results
    @Test
    void testSearchOrders() {
        when(ordersRepository.findByStatusAndOrderDateBetween(anyString(), any(), any()))
                .thenReturn(List.of(order));

        List<OrderResponseDTO> result =
                ordersService.searchOrders("Shipped",
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(1));

        assertFalse(result.isEmpty());
    }
}