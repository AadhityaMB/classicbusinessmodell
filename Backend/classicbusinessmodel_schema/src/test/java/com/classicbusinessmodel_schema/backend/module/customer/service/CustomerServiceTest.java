package com.classicbusinessmodel_schema.backend.module.customer.service;
import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerName("Test User");
        customer.setContactFirstName("John");
        customer.setContactLastName("Doe");
        customer.setCity("Chennai");
        customer.setCountry("India");
        customer.setCreditLimit(BigDecimal.valueOf(5000));
        customer.setOrders(new ArrayList<>());
        customer.setPayments(new ArrayList<>());    }

    // 1. GET ALL
    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(customer)));

        Page<?> result = customerService.getAllCustomers(PageRequest.of(0, 10));

        assertEquals(1, result.getContent().size());
    }

    // 2. GET BY ID
    @Test
    void testGetById() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        var result = customerService.getCustomerById(1);

        assertEquals("Test User", result.getCustomerName());
    }

    // 3. CREATE
    @Test
    void testCreateCustomer() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setCustomerNumber(1);
        dto.setCustomerName("Test User");
        dto.setContactFirstName("John");
        dto.setContactLastName("Doe");
        dto.setCity("Chennai");
        dto.setCountry("India");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        var result = customerService.createCustomer(dto);

        assertEquals("Test User", result.getCustomerName());
    }

    // 4. UPDATE
    @Test
    void testUpdateCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setCustomerName("Updated Name");

        var result = customerService.updateCustomer(1, dto);

        assertEquals("Updated Name", result.getCustomerName());
    }

    // 5. DELETE
    @Test
    void testDeleteCustomer() {
        // Arrange
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        // Act
        customerService.deleteCustomer(1);

        // Assert
        verify(customerRepository).findById(1);
        verify(customerRepository).delete(customer);
    }

    // 6. CREDIT LIMIT
    @Test
    void testCreditLimit() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        BigDecimal result = customerService.getCreditLimit(1);

        assertEquals(0, BigDecimal.valueOf(5000).compareTo(result));
    }

    // 7. SEARCH
    @Test
    void testSearch() {
        when(customerRepository.findByCountryAndCity("India", "Chennai"))
                .thenReturn(List.of(customer));

        var result = customerService.searchByGeography("India", "Chennai");

        assertFalse(result.isEmpty());
    }

    // 8. NOT FOUND CASE
    @Test
    void testCustomerNotFound() {
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.getCustomerById(99));
    }
}