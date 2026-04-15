
package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Search by city and country
    List<Customer> findByCityAndCountry(String city, String country);

    // Find by country
    List<Customer> findByCountry(String country);

    // Credit limit filter
    List<Customer> findByCreditLimitGreaterThan(BigDecimal amount);
}