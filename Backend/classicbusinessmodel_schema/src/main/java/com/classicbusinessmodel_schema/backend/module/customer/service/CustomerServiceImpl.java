package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.CustomerResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET ALL CUSTOMERS (PAGINATION)
    @Override
    public Page<CustomerResponseDTO> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(this::convert);
    }

    // GET CUSTOMER BY ID
    @Override
    public CustomerResponseDTO getCustomerById(Integer id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return convert(c);
    }

    // CREATE CUSTOMER
    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO r) {
        Customer c = new Customer();
        set(c, r);
        return convert(customerRepository.save(c));
    }

    // UPDATE CUSTOMER
    @Override
    public CustomerResponseDTO updateCustomer(Integer id, CustomerRequestDTO r) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        set(c, r);
        return convert(customerRepository.save(c));
    }

    // DELETE CUSTOMER
    @Override
    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        customerRepository.deleteByCustomerNumber(id);
    }

    // GET CREDIT LIMIT ONLY
    @Override
    public BigDecimal getCreditLimit(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"))
                .getCreditLimit();
    }

    // UPDATE CREDIT LIMIT
    @Override
    public CustomerResponseDTO updateCreditLimit(Integer id, BigDecimal limit) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        customerRepository.updateCreditLimit(id, limit);
        return getCustomerById(id);
    }

    // SEARCH CUSTOMERS BY COUNTRY / CITY
    @Override
    public List<CustomerResponseDTO> searchByGeography(String country, String city) {
        List<Customer> customers;

        if (country != null && city != null) {
            customers = customerRepository.findByCountryAndCity(country, city);
        } else if (country != null) {
            customers = customerRepository.findByCountry(country);
        } else if (city != null) {
            customers = customerRepository.findByCity(city);
        } else {
            customers = customerRepository.findAll();
        }

        List<CustomerResponseDTO> list = new ArrayList<>();

        // Convert entity list to DTO list
        return customers.stream()
                .map(this::convert)
                .toList();
    }

    // MAP DTO → ENTITY
    private void set(Customer c, CustomerRequestDTO r) {
        c.setCustomerNumber(r.getCustomerNumber());
        c.setCustomerName(r.getCustomerName());
        c.setContactLastName(r.getContactLastName());
        c.setContactFirstName(r.getContactFirstName());
        c.setPhone(r.getPhone());
        c.setAddressLine1(r.getAddressLine1());
        c.setAddressLine2(r.getAddressLine2());
        c.setCity(r.getCity());
        c.setState(r.getState());
        c.setPostalCode(r.getPostalCode());
        c.setCountry(r.getCountry());
        c.setCreditLimit(r.getCreditLimit());

        // Assign sales representative if provided
        if (r.getSalesRepEmployeeNumber() != null) {
            Employee e = employeeRepository.findById(r.getSalesRepEmployeeNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            c.setSalesRep(e);
        } else {
            c.setSalesRep(null);
        }
    }

    // MAP ENTITY → DTO
    private CustomerResponseDTO convert(Customer c) {
        CustomerResponseDTO d = new CustomerResponseDTO();

        d.setCustomerNumber(c.getCustomerNumber());
        d.setCustomerName(c.getCustomerName());
        d.setContactLastName(c.getContactLastName());
        d.setContactFirstName(c.getContactFirstName());
        d.setPhone(c.getPhone());
        d.setAddressLine1(c.getAddressLine1());
        d.setAddressLine2(c.getAddressLine2());
        d.setCity(c.getCity());
        d.setState(c.getState());
        d.setPostalCode(c.getPostalCode());
        d.setCountry(c.getCountry());
        d.setCreditLimit(c.getCreditLimit());

        // Flatten sales rep details
        if (c.getSalesRep() != null) {
            d.setSalesRepEmployeeNumber(c.getSalesRep().getEmployeeNumber());
            d.setSalesRepName(c.getSalesRep().getFirstName() + " " + c.getSalesRep().getLastName());
        }

        return d;
    }
}