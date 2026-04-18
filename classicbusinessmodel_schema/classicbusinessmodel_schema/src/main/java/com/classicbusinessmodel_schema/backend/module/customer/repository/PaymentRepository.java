package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // 1. Get payments for a customer
    List<Payment> findByCustomerCustomerNumber(Integer customerNumber);

    // 2. Get payment by check number
    Optional<Payment> findByIdCheckNumber(String checkNumber);
    //3.READ – Get payments by customer
    @Query("SELECT p FROM Payment p WHERE p.customer.customerNumber = :customerNumber")
    List<Payment> getPaymentsByCustomer(@Param("customerNumber") Integer customerNumber);

    //4.READ – Get single payment
    @Query("SELECT p FROM Payment p WHERE p.id.customerNumber = :customerNumber AND p.id.checkNumber = :checkNumber")
    Payment getPayment(@Param("customerNumber") Integer customerNumber,
                       @Param("checkNumber") String checkNumber);

    //5.UPDATE – Update payment amount
    @Modifying
    @Query("UPDATE Payment p SET p.amount = :amount WHERE p.id.customerNumber = :customerNumber AND p.id.checkNumber = :checkNumber")
    int updatePaymentAmount(@Param("customerNumber") Integer customerNumber,
                            @Param("checkNumber") String checkNumber,
                            @Param("amount") BigDecimal amount);

    //6.DELETE – Delete payment
    @Modifying
    @Query("DELETE FROM Payment p WHERE p.id.customerNumber = :customerNumber AND p.id.checkNumber = :checkNumber")
    void deletePayment(@Param("customerNumber") Integer customerNumber,
                       @Param("checkNumber") String checkNumber);
}