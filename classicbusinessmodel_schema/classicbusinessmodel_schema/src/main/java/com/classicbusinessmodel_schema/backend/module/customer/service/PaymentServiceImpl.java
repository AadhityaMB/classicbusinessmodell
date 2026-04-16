package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> getPaymentsByCustomer(Integer customerId) {
        return repository.findByCustomerCustomerNumber(customerId);
    }

    @Override
    public List<Payment> getPaymentsBetweenDates(LocalDate start, LocalDate end) {
        return repository.findByPaymentDateBetween(start, end);
    }
}
