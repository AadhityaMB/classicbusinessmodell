package com.classicbusinessmodel_schema.backend.module.customer.repository;


import com.classicbusinessmodel_schema.backend.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
    class CustomerRepositoryTest {

        @Autowired
        private CustomerRepository repository;

        // 🔧 Create test entity
        private Customer createCustomer() {
            Customer customer = new Customer();
            customer.setCustomerNumber(1);
            customer.setCustomerName("Adhi");
            customer.setContactFirstName("Aadhitya");
            customer.setContactLastName("MB");
            customer.setPhone("1234567890");
            customer.setAddressLine1("Anna Nagar");
            customer.setCity("Chennai");
            customer.setCountry("India");
            customer.setCreditLimit(BigDecimal.valueOf(50000));
            return customer;
        }

        // ✅ CREATE
        @Test
        @DisplayName("Test Save Customer")
        void testSaveCustomer() {

            Customer customer = createCustomer();

            Customer saved = repository.save(customer);

            assertNotNull(saved);
            assertEquals("Adhi", saved.getCustomerName());
        }

        // ✅ READ BY ID
        @Test
        @DisplayName("Test Find Customer By ID")
        void testFindById() {

            Customer customer = repository.save(createCustomer());

            Optional<Customer> found = repository.findById(customer.getCustomerNumber());

            assertTrue(found.isPresent());
            assertEquals("Adhi", found.get().getCustomerName());
        }

        // ✅ READ ALL
        @Test
        @DisplayName("Test Find All Customers")
        void testFindAll() {

            repository.save(createCustomer());

            List<Customer> customers = repository.findAll();

            assertFalse(customers.isEmpty());
            assertEquals(1, customers.size());
        }

        // ✅ UPDATE
        @Test
        @DisplayName("Test Update Customer")
        void testUpdateCustomer() {

            Customer customer = repository.save(createCustomer());

            customer.setCustomerName("Updated Name");

            Customer updated = repository.save(customer);

            assertEquals("Updated Name", updated.getCustomerName());
        }

        // ✅ DELETE
        @Test
        @DisplayName("Test Delete Customer")
        void testDeleteCustomer() {

            Customer customer = repository.save(createCustomer());

            repository.deleteById(customer.getCustomerNumber());

            Optional<Customer> deleted = repository.findById(customer.getCustomerNumber());

            assertFalse(deleted.isPresent());
        }

}
