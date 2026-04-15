package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // Get payments for customer
    List<Payment> findByCustomerCustomerNumber(Integer customerNumber);

    // Date filter
    List<Payment> findByPaymentDateBetween(LocalDate start, LocalDate end);
}