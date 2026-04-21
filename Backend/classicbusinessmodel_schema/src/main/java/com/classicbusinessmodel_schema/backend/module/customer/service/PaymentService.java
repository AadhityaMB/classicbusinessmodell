package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    Page<PaymentResponseDTO> getAllPayments(Pageable pageable);
    List<PaymentResponseDTO> getPaymentsByCustomer(Integer customerNumber);

    PaymentResponseDTO getPaymentByCheckNumber(String checkNumber);

    PaymentResponseDTO createPayment(PaymentResponseDTO dto);
}