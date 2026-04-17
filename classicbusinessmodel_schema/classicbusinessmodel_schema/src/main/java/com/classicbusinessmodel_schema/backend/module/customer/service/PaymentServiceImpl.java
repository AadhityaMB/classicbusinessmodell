package com.classicbusinessmodel_schema.backend.module.customer.service;


import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    // Get all payments for a customer
    public List<Payment> getPaymentsByCustomer(Integer customerNumber) {
        return paymentRepository.findByCustomerCustomerNumber(customerNumber);
    }

    // Get payments between dates
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    // Update payment amount using custom query
    public void updatePaymentAmount(Integer customerNumber, String checkNumber, BigDecimal amount) {
        if (!paymentRepository.existsById(
                new PaymentId(customerNumber, checkNumber))) {
            throw new ResourceNotFoundException("Payment not found");
        }    }

    // Delete payment using composite key fields
    public void deletePayment(Integer customerNumber, String checkNumber) {
        if (!paymentRepository.existsById(
                new PaymentId(customerNumber, checkNumber))) {
            throw new ResourceNotFoundException("Payment not found");
        }    }
}