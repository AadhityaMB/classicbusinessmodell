package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Integer id);

    Customer updateCustomer(Integer id, Customer customer);

    void deleteCustomer(Integer id);

    BigDecimal getCreditLimit(Integer id);

    Customer updateCreditLimit(Integer id, BigDecimal creditLimit);

    List<Customer> searchCustomers(String city, String country);
}
