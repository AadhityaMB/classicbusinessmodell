package com.classicbusinessmodel_schema.backend.module.report.repository;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReportRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Customer customer;
    private Orders order;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerNumber(501);
        customer.setCustomerName("Report Test Corp");
        customer.setContactFirstName("Adhi");
        customer.setContactLastName("MB");
        customer.setPhone("9999999999");
        customer.setAddressLine1("Test Street");
        customer.setCity("Chennai");
        customer.setCountry("India");
        customer.setCreditLimit(BigDecimal.valueOf(20000));
        entityManager.persist(customer);

        order = new Orders();
        order.setOrderNumber(9001);
        order.setOrderDate(LocalDate.of(2024, 6, 10));
        order.setRequiredDate(LocalDate.of(2024, 6, 20));
        order.setStatus("Shipped");
        order.setCustomer(customer);
        entityManager.persist(order);

        entityManager.flush();
    }

    // 1. Find all customers (used by getCustomerExposure, getSalesByCountry, getSalesByEmployee)
    @Test
    @DisplayName("findAll customers returns persisted customers")
    void testFindAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        assertFalse(customers.isEmpty());
        assertTrue(customers.stream().anyMatch(c -> c.getCustomerNumber() == 501));
    }

    // 2. Find orders by customer number (used by getCustomerExposure, getSalesByCountry, getSalesByEmployee)
    @Test
    @DisplayName("findByCustomerCustomerNumber returns correct orders")
    void testFindOrdersByCustomerNumber() {
        List<Orders> orders = ordersRepository.findByCustomerCustomerNumber(501);

        assertEquals(1, orders.size());
        assertEquals(9001, orders.get(0).getOrderNumber());
        assertEquals("Shipped", orders.get(0).getStatus());
    }

    // 3. Find orders by customer number - unknown customer returns empty
    @Test
    @DisplayName("findByCustomerCustomerNumber returns empty list for unknown customer")
    void testFindOrdersByCustomerNumber_Unknown() {
        List<Orders> orders = ordersRepository.findByCustomerCustomerNumber(99999);

        assertTrue(orders.isEmpty());
    }

    // 4. findByOrderOrderNumber returns empty when no order details exist
    @Test
    @DisplayName("findByOrderOrderNumber returns empty list when no details exist for order")
    void testFindOrderDetails_Empty() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<OrderDetails> details =
                orderDetailRepository.findByOrder_OrderNumber(9001, pageable);

        assertTrue(details.isEmpty());
    }

    // 5. Find all orders (used by getMonthlyRevenue)
    @Test
    @DisplayName("findAll orders returns all persisted orders")
    void testFindAllOrders() {
        List<Orders> orders = ordersRepository.findAll();

        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().anyMatch(o -> o.getOrderNumber() == 9001));
    }

    // 6. Order date is persisted and retrieved correctly (used by getMonthlyRevenue key calculation)
    @Test
    @DisplayName("Order date year and month are persisted correctly")
    void testOrderDate() {
        Orders found = ordersRepository.findById(9001).orElseThrow();

        assertEquals(LocalDate.of(2024, 6, 10), found.getOrderDate());
        assertEquals(2024, found.getOrderDate().getYear());
        assertEquals(6, found.getOrderDate().getMonthValue());
    }

    // 7. Customer credit limit is stored correctly (used by getCustomerExposure, getHighRiskCustomers)
    @Test
    @DisplayName("Customer credit limit is stored and retrieved correctly")
    void testCustomerCreditLimit() {
        Customer found = customerRepository.findById(501).orElseThrow();

        assertEquals(0, BigDecimal.valueOf(20000).compareTo(found.getCreditLimit()));
    }

    // 8. Customer country is stored correctly (used by getSalesByCountry)
    @Test
    @DisplayName("Customer country is stored and retrieved correctly")
    void testCustomerCountry() {
        Customer found = customerRepository.findById(501).orElseThrow();

        assertEquals("India", found.getCountry());
    }

    // 9. Find order by ID - positive case
    @Test
    @DisplayName("findById returns correct order with customer relationship")
    void testFindOrderById() {
        Optional<Orders> found = ordersRepository.findById(9001);

        assertTrue(found.isPresent());
        assertEquals(customer.getCustomerNumber(), found.get().getCustomer().getCustomerNumber());
    }

    // 10. Find order by ID - negative case
    @Test
    @DisplayName("findById returns empty for non-existent order")
    void testFindOrderById_NotFound() {
        Optional<Orders> found = ordersRepository.findById(99999);

        assertFalse(found.isPresent());
    }
}
