package com.classicbusinessmodel_schema.backend.module.customer.service;


import com.classicbusinessmodel_schema.backend.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    // Get payments by customer
    List<Payment> getPaymentsByCustomer(Integer customerNumber);

    // Get payments by date range
    List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate);

    // Update payment amount
    void updatePaymentAmount(Integer customerNumber, String checkNumber, BigDecimal amount);

    // Delete payment
    void deletePayment(Integer customerNumber, String checkNumber);
}