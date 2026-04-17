package com.classicbusinessmodel_schema.backend.module.customer.service;


import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.dto.request.CustomerRequestDTO;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.CustomerResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.repository.CustomerRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    // Get all customers and convert to DTO
    public Page<CustomerResponseDTO> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(this::convert);
    }

    // Get customer by ID
    public CustomerResponseDTO getCustomerById(Integer id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return convert(c);
    }

    // Create new customer
    public CustomerResponseDTO createCustomer(CustomerRequestDTO r) {
        Customer c = new Customer();
        set(c, r);
        return convert(customerRepository.save(c));
    }

    // Update existing customer
    public CustomerResponseDTO updateCustomer(Integer id, CustomerRequestDTO r) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        set(c, r);
        return convert(customerRepository.save(c));
    }

    // Delete using custom query
    public void deleteCustomer(Integer id) {
        customerRepository.deleteByCustomerNumber(id);
    }

    // Get only credit limit
    public BigDecimal getCreditLimit(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"))
                .getCreditLimit();
    }

    // Update only credit limit (custom query)
    public CustomerResponseDTO updateCreditLimit(Integer id, BigDecimal limit) {
        customerRepository.updateCreditLimit(id, limit);
        return getCustomerById(id);
    }

    // Search logic based on optional params
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
        for (Customer c : customers) {
            list.add(convert(c));
        }
        return list;
    }

    // Map DTO → Entity
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

        // Set sales rep if provided
        if (r.getSalesRepEmployeeNumber() != null) {
            Employee e = employeeRepository.findById(r.getSalesRepEmployeeNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            c.setSalesRep(e);
        }
    }

    // Map Entity → DTO
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