package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentResponseDTO> getAllPayments();

    List<PaymentResponseDTO> getPaymentsByCustomer(Integer customerNumber);

    PaymentResponseDTO getPaymentByCheckNumber(String checkNumber);

    PaymentResponseDTO createPayment(PaymentResponseDTO dto);
}