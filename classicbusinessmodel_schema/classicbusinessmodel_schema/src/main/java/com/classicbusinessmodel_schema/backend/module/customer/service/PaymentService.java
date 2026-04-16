package com.classicbusinessmodel_schema.backend.module.customer.service;


import com.classicbusinessmodel_schema.backend.entity.Payment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    List<Payment> getPaymentsByCustomer(Integer customerId);

    List<Payment> getPaymentsBetweenDates(LocalDate start, LocalDate end);
}
