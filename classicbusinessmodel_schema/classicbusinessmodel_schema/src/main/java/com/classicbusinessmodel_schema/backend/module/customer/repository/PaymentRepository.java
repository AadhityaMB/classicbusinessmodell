package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // ── SELECT ──

    // Get payments for a customer
    List<Payment> findByCustomerCustomerNumber(Integer customerNumber);

    // Get payments between dates
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);


    // ── UPDATE ──

    @Modifying
    @Query("UPDATE Payment p SET p.amount = :amount WHERE p.customer.customerNumber = :customerNumber AND p.checkNumber = :checkNumber")
    int updatePaymentAmount(@Param("customerNumber") Integer customerNumber,
                            @Param("checkNumber") String checkNumber,
                            @Param("amount") BigDecimal amount);


    // ── DELETE ──

    @Modifying
    @Query("DELETE FROM Payment p WHERE p.customer.customerNumber = :customerNumber AND p.checkNumber = :checkNumber")
    void deletePayment(@Param("customerNumber") Integer customerNumber,
                       @Param("checkNumber") String checkNumber);
}