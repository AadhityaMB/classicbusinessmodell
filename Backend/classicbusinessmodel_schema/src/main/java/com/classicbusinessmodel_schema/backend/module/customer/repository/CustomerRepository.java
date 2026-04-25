package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Find by country
    List<Customer> findByCountry(String country);

    // Find by city
    List<Customer> findByCity(String city);

    // Find customers by country and city
    List<Customer> findByCountryAndCity(String country, String city);

    // Update credit limit
    @Modifying
    @Query("UPDATE Customer c SET c.creditLimit = :creditLimit WHERE c.customerNumber = :customerNumber")
    int updateCreditLimit(@Param("customerNumber") Integer customerNumber,
                          @Param("creditLimit") BigDecimal creditLimit);


    // Delete customer by ID
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.customerNumber = :customerNumber")
    void deleteByCustomerNumber(@Param("customerNumber") Integer customerNumber);


}