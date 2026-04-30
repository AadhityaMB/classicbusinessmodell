package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
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
import java.util.List;

// Marks this class as service layer
@Service
// Enables transaction management (DB operations)
@Transactional
public class CustomerServiceImpl implements CustomerService {

    // Injects CustomerRepository
    @Autowired
    private CustomerRepository customerRepository;

    // Injects EmployeeRepository
    @Autowired
    private EmployeeRepository employeeRepository;

    // Fetch all customers with pagination
    @Override
    public Page<CustomerResponseDTO> getAllCustomers(Pageable pageable) {

        // Fetch from DB and convert Entity → DTO
        return customerRepository.findAll(pageable)
                .map(this::convert);
    }

    // Fetch single customer by ID
    @Override
    public CustomerResponseDTO getCustomerById(Integer id) {

        // Find customer or throw exception
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID "+id));

        // Convert to DTO
        return convert(c);
    }

    // Create new customer
    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO r) {
        // Check if customer already exists
        if (customerRepository.existsById(r.getCustomerNumber())) {
            throw new ResourceAlreadyExistsException("Customer already exists with id: " + r.getCustomerNumber());
        }
        Customer c = new Customer();

        // Map DTO → Entity
        set(c, r);

        // Save to DB and convert to DTO
        return convert(customerRepository.save(c));
    }

    // Update existing customer
    @Override
    public CustomerResponseDTO updateCustomer(Integer id, CustomerRequestDTO r) {

        // Fetch existing customer
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID "+id));

        // Update fields
        set(c, r);

        // Save updated entity
        return convert(customerRepository.save(c));
    }

    // Delete customer
    @Override
    public void deleteCustomer(Integer id) {

        // Fetch customer
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID "+id));

        // Clear child relationships (avoid foreign key issues)
        customer.getOrders().clear();
        customer.getPayments().clear();

        // Delete from DB
        customerRepository.delete(customer);
    }

    // Get only credit limit
    @Override
    public BigDecimal getCreditLimit(Integer id) {

        // Fetch customer and return credit limit
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID "+id))
                .getCreditLimit();
    }

    // Update only credit limit
    @Override
    public CustomerResponseDTO updateCreditLimit(Integer id, BigDecimal limit) {

        // Check if customer exists
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with ID "+ id);
        }

        // Execute custom update query
        customerRepository.updateCreditLimit(id, limit);

        // Return updated customer
        return getCustomerById(id);
    }

    // Search customers based on country/city
    @Override
    public List<CustomerResponseDTO> searchByGeography(String country, String city) {
        List<Customer> customers;

        // Apply filters based on input
        if (country != null && city != null) {
            customers = customerRepository.findByCountryAndCity(country, city);
        } else if (country != null) {
            customers = customerRepository.findByCountry(country);
        } else if (city != null) {
            customers = customerRepository.findByCity(city);
        } else {
            customers = customerRepository.findAll();
        }
        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("No customers found for given search criteria");
        }

        // Convert Entity list → DTO list
        return customers.stream()
                .map(this::convert)
                .toList();
    }

    // Convert DTO → Entity
    private void set(Customer c, CustomerRequestDTO r) {

        // Set basic fields
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

        // Assign sales rep if present
        if (r.getSalesRepEmployeeNumber() != null) {

            // Fetch employee
            Employee e = employeeRepository.findById(r.getSalesRepEmployeeNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            c.setSalesRep(e);
        } else {
            c.setSalesRep(null);
        }
    }

    // Convert Entity → DTO
    private CustomerResponseDTO convert(Customer c) {

        // Create DTO object
        CustomerResponseDTO d = new CustomerResponseDTO();

        // Map basic fields
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

        // Map sales rep details if exists
        if (c.getSalesRep() != null) {
            d.setSalesRepEmployeeNumber(c.getSalesRep().getEmployeeNumber());
            d.setSalesRepName(c.getSalesRep().getFirstName() + " " + c.getSalesRep().getLastName());
        }

        return d;
    }
}