package com.classicbusinessmodel_schema.backend.module.customer.service;


import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Override
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    // READ ALL
    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // READ BY ID
    @Override
    public Customer getCustomerById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    // UPDATE
    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existing = getCustomerById(id);

        existing.setCustomerName(customer.getCustomerName());
        existing.setContactFirstName(customer.getContactFirstName());
        existing.setContactLastName(customer.getContactLastName());
        existing.setPhone(customer.getPhone());
        existing.setCity(customer.getCity());
        existing.setCountry(customer.getCountry());

        return repository.save(existing);
    }

    // DELETE
    @Override
    public void deleteCustomer(Integer id) {
        Customer existing = getCustomerById(id);
        repository.delete(existing);
    }

    // CREDIT LIMIT GET
    @Override
    public BigDecimal getCreditLimit(Integer id) {
        return getCustomerById(id).getCreditLimit();
    }

    // CREDIT LIMIT UPDATE
    @Override
    public Customer updateCreditLimit(Integer id, BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Credit limit cannot be negative");
        }

        Customer customer = getCustomerById(id);
        customer.setCreditLimit(creditLimit);

        return repository.save(customer);
    }

    // SEARCH
    @Override
    public List<Customer> searchCustomers(String city, String country) {
        return repository.findByCityAndCountry(city, country);
    }
}