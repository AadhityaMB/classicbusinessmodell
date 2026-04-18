package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    // Inject repository using @Autowired
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentServiceImpl() {
    }

    @Override
    public Payment getPayment(Integer customerNumber, String checkNumber) {

        return paymentRepository.getPayment(customerNumber, checkNumber);
    }

    @Override
    public Payment createPayment(Payment payment) {

        return paymentRepository.save(payment);
    }
    // GET ALL PAYMENTS BY CUSTOMER ID
    @Override
    public List<Payment> getPaymentsByCustomer(Integer customerNumber) {
        return paymentRepository.findByCustomerCustomerNumber(customerNumber);
    }

    // GET PAYMENTS BETWEEN DATE RANGE
    @Override
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    // UPDATE PAYMENT AMOUNT (CHECK EXISTENCE FIRST)
    @Override
    public void updatePaymentAmount(Integer customerNumber, String checkNumber, BigDecimal amount) {

        // check if payment exists using composite key
        if (!paymentRepository.existsById(
                new PaymentId(customerNumber, checkNumber))) {

            throw new ResourceNotFoundException("Payment not found");
        }
        paymentRepository.updatePaymentAmount(customerNumber, checkNumber, amount);
    }

    // DELETE PAYMENT (CHECK EXISTENCE FIRST)
    @Override
    public void deletePayment(Integer customerNumber, String checkNumber) {

        // verify payment exists before deleting
        if (!paymentRepository.existsById(
                new PaymentId(customerNumber, checkNumber))) {

            throw new ResourceNotFoundException("Payment not found");
        }

        paymentRepository.deletePayment(customerNumber, checkNumber);
    }
}