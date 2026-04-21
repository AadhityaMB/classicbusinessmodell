package com.classicbusinessmodel_schema.backend.module.orders.repository;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private TestEntityManager entityManager;

    // Helper method to create customer
    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setCustomerNumber((int) (Math.random() * 100000));
        customer.setCustomerName("Test Customer");
        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");

        return entityManager.persist(customer);
    }

    // Helper method to create order
    private Orders createOrder(Customer customer) {
        Orders order = new Orders();
        order.setOrderNumber((int) (Math.random() * 100000));
        order.setOrderDate(LocalDate.now());
        order.setRequiredDate(LocalDate.now().plusDays(5));
        order.setShippedDate(LocalDate.now());
        order.setStatus("Shipped");
        order.setComments("Test Order");
        order.setCustomer(customer);

        return ordersRepository.save(order);
    }

    // 1.  Positive - Create order
    @Test
    void testCreateOrder() {
        Customer customer = createCustomer();

        Orders order = new Orders();
        order.setOrderNumber((int) (Math.random() * 100000));
        order.setOrderDate(LocalDate.now());
        order.setRequiredDate(LocalDate.now().plusDays(5));
        order.setShippedDate(LocalDate.now());
        order.setStatus("Shipped");
        order.setComments("Created Order");
        order.setCustomer(customer);

        Orders savedOrder = ordersRepository.save(order);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getOrderNumber());
        assertEquals("Shipped", savedOrder.getStatus());
    }

    // 2. Positive - Read order by ID
    @Test
    void testReadOrderById() {
        Customer customer = createCustomer();
        Orders savedOrder = createOrder(customer);

        Optional<Orders> foundOrder = ordersRepository.findById(savedOrder.getOrderNumber());

        assertTrue(foundOrder.isPresent());
        assertEquals(savedOrder.getOrderNumber(), foundOrder.get().getOrderNumber());
    }

    // 3.  Positive - Update order
    @Test
    void testUpdateOrder() {
        Customer customer = createCustomer();
        Orders savedOrder = createOrder(customer);

        savedOrder.setStatus("Cancelled");
        savedOrder.setComments("Updated Order");

        Orders updatedOrder = ordersRepository.save(savedOrder);

        assertEquals("Cancelled", updatedOrder.getStatus());
        assertEquals("Updated Order", updatedOrder.getComments());
    }

    // 4.  Positive - Delete order
    @Test
    void testDeleteOrder() {
        Customer customer = createCustomer();
        Orders savedOrder = createOrder(customer);

        ordersRepository.deleteById(savedOrder.getOrderNumber());

        Optional<Orders> deletedOrder = ordersRepository.findById(savedOrder.getOrderNumber());

        assertFalse(deletedOrder.isPresent());
    }

    // 5.  Negative - Read order by invalid ID
    @Test
    void testReadOrderByInvalidId() {
        Optional<Orders> foundOrder = ordersRepository.findById(999999);

        assertFalse(foundOrder.isPresent());
    }
}