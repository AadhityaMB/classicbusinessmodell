package com.classicbusinessmodel_schema.backend.module.customer.controller;


import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CREATE
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    // READ ALL
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    // UPDATE
//    @PutMapping("/{id}")
//    public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
//        customer.setCustomerNumber(id);
//        return customerRepository.save(customer);
//    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}
