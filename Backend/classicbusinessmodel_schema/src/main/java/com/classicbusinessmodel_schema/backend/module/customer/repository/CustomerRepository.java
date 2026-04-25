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
    // 1.Find by country
    List<Customer> findByCountry(String country);

    // 2.Find by city
    List<Customer> findByCity(String city);

    // 3. Find customers by country and city
    List<Customer> findByCountryAndCity(String country, String city);

    // 4. Find customers by state
    List<Customer> findByState(String state);

    // 5. Get customers without sales representative
    @Query("SELECT c FROM Customer c WHERE c.salesRep IS NULL")
    List<Customer> findCustomersWithoutSalesRep();

    // 6. Find customers by phone number
    @Query("SELECT c FROM Customer c WHERE c.phone = :phone")
    List<Customer> findByPhone(@Param("phone") String phone);

    // 7. Update credit limit
    @Modifying
    @Query("UPDATE Customer c SET c.creditLimit = :creditLimit WHERE c.customerNumber = :customerNumber")
    int updateCreditLimit(@Param("customerNumber") Integer customerNumber,
                          @Param("creditLimit") BigDecimal creditLimit);


    // 8. Delete customer by ID
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.customerNumber = :customerNumber")
    void deleteByCustomerNumber(@Param("customerNumber") Integer customerNumber);

    // 9. Find customers by contact first name
    @Query("SELECT c FROM Customer c WHERE c.contactFirstName = :firstName")
    List<Customer> findByContactFirstName(@Param("firstName") String firstName);

}