package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.*;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import org.junit.jupiter.api.DisplayName;
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

    // Create ProductLine
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");
        return productLineRepository.save(pl);
    }

    // Create Product
    private Product createProduct(ProductLine pl) {
        Product product = new Product();
        product.setProductCode("S10_TEST");
        product.setProductName("Test Car");
        product.setProductLine(pl);
        product.setProductScale("1:10");
        product.setProductVendor("Test Vendor");
        product.setProductDescription("Test Desc");
        product.setQuantityInStock(100);
        product.setBuyPrice(BigDecimal.valueOf(50));
        product.setMSRP(BigDecimal.valueOf(100));
        return productRepository.save(product);
    }

    // Create Customer
    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerName("Test Customer");
        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");
        customer.setPhone("1234567890");
        customer.setAddressLine1("Test Address");
        customer.setCity("Chennai");
        customer.setCountry("India");
        customer.setCreditLimit(BigDecimal.valueOf(50000));
        return customerRepository.save(customer);
    }

    // Create Order
    private Orders createOrder(Customer customer) {
        Orders order = new Orders();
        order.setOrderNumber(1);
        order.setCustomer(customer);
        return ordersRepository.save(order);
    }

    // Create OrderDetails
    private OrderDetails createOrderDetails(Orders order, Product product) {
        OrderDetails od = new OrderDetails();
        od.setOrder(order);
        od.setProduct(product);
        od.setQuantityOrdered(5);
        od.setPriceEach(BigDecimal.valueOf(100));
        od.setOrderLineNumber(1);
        return od;
    }

    // CREATE
    @Test
    @DisplayName("Save OrderDetails")
    void saveOrderDetails() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        OrderDetails saved = repository.save(createOrderDetails(order, product));

        assertNotNull(saved);
        assertEquals(5, saved.getQuantityOrdered());
    }

    // READ BY ID
    @Test
    @DisplayName("Find OrderDetails By ID")
    void findById() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        OrderDetails saved = repository.save(createOrderDetails(order, product));

        OrderDetailsId id = new OrderDetailsId(order.getOrderNumber(), product.getProductCode());

        Optional<OrderDetails> found = repository.findById(id);

        assertTrue(found.isPresent());
        assertEquals(5, found.get().getQuantityOrdered());
    }

    // READ ALL
    @Test
    @DisplayName("Find All OrderDetails")
    void findAll() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);
        Customer customer = createCustomer();
        Orders order = createOrder(customer);

        repository.save(createOrderDetails(order, product));

        List<OrderDetails> list = repository.findAll();

        assertEquals(1, list.size());
    }

    // UPDATE
    @Test
    @DisplayName("Update OrderDetails")
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

    // DELETE
    @Test
    @DisplayName("Delete OrderDetails")
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