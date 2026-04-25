package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // 1. Get payments for a customer
    List<Payment> findByCustomerCustomerNumber(Integer customerNumber);

    // 2. Get payment by check number
    Optional<Payment> findByIdCheckNumber(String checkNumber);



}