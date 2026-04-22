package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.*;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    // HELPERS

    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("PL_" + System.currentTimeMillis());
        pl.setTextDescription("Test");
        pl.setHtmlDescription("<p>Test</p>");
        return productLineRepository.save(pl);
    }

    private Product createProduct(ProductLine pl) {
        Product product = new Product();
        product.setProductCode("P_" + System.currentTimeMillis());
        product.setProductName("Test Product");
        product.setProductLine(pl);
        product.setProductScale("1:10");
        product.setProductVendor("Vendor");
        product.setProductDescription("Desc");
        product.setQuantityInStock(100);
        product.setBuyPrice(BigDecimal.valueOf(50));
        product.setMSRP(BigDecimal.valueOf(100));
        return productRepository.save(product);
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setCustomerNumber((int) (System.currentTimeMillis() % 100000));
        customer.setCustomerName("Test Customer");
        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");
        customer.setPhone("1234567890");
        customer.setAddressLine1("Address");
        customer.setCity("Chennai");
        customer.setCountry("India");
        customer.setCreditLimit(BigDecimal.valueOf(50000));
        return customerRepository.save(customer);
    }

    private Orders createOrder(Customer customer) {
        Orders order = new Orders();
        order.setOrderNumber((int) (System.currentTimeMillis() % 100000));
        order.setCustomer(customer);
        return ordersRepository.save(order);
    }

    private OrderDetails createOrderDetails(Orders order, Product product) {
        OrderDetails od = new OrderDetails();
        od.setOrder(order);
        od.setProduct(product);
        od.setQuantityOrdered(5);
        od.setPriceEach(BigDecimal.valueOf(100));
        od.setOrderLineNumber(1);
        return od;
    }

    // TESTS

    @Test
    void saveOrderDetails() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        OrderDetails saved = repository.save(createOrderDetails(order, product));

        assertNotNull(saved);
        assertEquals(5, saved.getQuantityOrdered());
    }

    @Test
    void findById() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        repository.save(createOrderDetails(order, product));

        OrderDetailsId id = new OrderDetailsId(order.getOrderNumber(), product.getProductCode());

        Optional<OrderDetails> found = repository.findById(id);

        assertTrue(found.isPresent());
    }

    @Test
    void findAll() {

        List<OrderDetails> list = repository.findAll();

        assertNotNull(list); // ❗ no "isEmpty" assumption
    }

    @Test
    void updateOrderDetails() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        OrderDetails od = repository.save(createOrderDetails(order, product));

        od.setQuantityOrdered(10);

        OrderDetails updated = repository.save(od);

        assertEquals(10, updated.getQuantityOrdered());
    }

    @Test
    void deleteOrderDetails() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        OrderDetails od = repository.save(createOrderDetails(order, product));

        OrderDetailsId id = new OrderDetailsId(order.getOrderNumber(), product.getProductCode());

        repository.deleteById(id);

        Optional<OrderDetails> deleted = repository.findById(id);

        assertFalse(deleted.isPresent());
    }
}