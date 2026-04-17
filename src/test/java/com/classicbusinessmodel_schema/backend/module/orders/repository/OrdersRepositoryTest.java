package com.classicbusinessmodel_schema.backend.module.orders.repository;

import com.classicbusinessmodel_schema.backend.entity.*;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private TestEntityManager entityManager;

    // Helper method
    private Orders createOrder(String status, LocalDate orderDate, LocalDate shippedDate) {

        Customer customer = new Customer();
        customer.setCustomerNumber((int)(Math.random() * 1000));
        customer.setCustomerName("Test Customer");

        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");

        entityManager.persist(customer);

        Orders order = Orders.builder()
                .orderNumber((int) (Math.random() * 10000))
                .orderDate(orderDate)
                .requiredDate(orderDate.plusDays(5))
                .shippedDate(shippedDate)
                .status(status)
                .customer(customer)
                .build();

        return entityManager.persist(order);
    }

    // 1. Test find by status
    @Test
    void testFindByStatus() {
        createOrder("Shipped", LocalDate.now(), LocalDate.now());

        List<Orders> result = ordersRepository.findByStatus("Shipped");

        assertFalse(result.isEmpty());
    }

    // 2. Test find by date range
    @Test
    void testFindByOrderDateBetween() {
        createOrder("Pending", LocalDate.now(), null);

        List<Orders> result = ordersRepository.findByOrderDateBetween(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        assertTrue(result.size() > 0);
    }

    // 3. Test unshipped orders
    @Test
    void testFindUnshippedOrders() {
        createOrder("Pending", LocalDate.now(), null);

        List<Orders> result = ordersRepository.findUnshippedOrders();

        assertEquals(1, result.size());
    }

    // 4. Test orders after date
    @Test
    void testFindOrdersAfterDate() {
        createOrder("Shipped", LocalDate.now(), LocalDate.now());

        List<Orders> result = ordersRepository.findOrdersAfterDate(
                LocalDate.now().minusDays(1)
        );

        assertFalse(result.isEmpty());
    }

    // 5. Test count by status
    @Test
    void testCountByStatus() {
        createOrder("Shipped", LocalDate.now(), LocalDate.now());

        long count = ordersRepository.countByStatus("Shipped");

        assertEquals(1, count);
    }
}

