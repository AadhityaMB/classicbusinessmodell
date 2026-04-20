package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // ENTITY → DTO
    private PaymentResponseDTO mapToDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId().getCheckNumber(),
                payment.getCustomer().getCustomerNumber(),
                payment.getAmount(),
                payment.getPaymentDate()
        );
    }

    // DTO → ENTITY
    private Payment mapToEntity(PaymentResponseDTO dto) {

        Payment payment = new Payment();

        PaymentId id = new PaymentId();
        id.setCheckNumber(dto.getCheckNumber());
        id.setCustomerNumber(dto.getCustomerNumber());

        payment.setId(id);
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(dto.getPaymentDate());

        return payment;
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByCustomer(Integer customerNumber) {
        return paymentRepository.findByCustomerCustomerNumber(customerNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public PaymentResponseDTO getPaymentByCheckNumber(String checkNumber) {
        Payment payment = paymentRepository.findByIdCheckNumber(checkNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return mapToDTO(payment);
    }

    @Override
    public PaymentResponseDTO createPayment(PaymentResponseDTO dto) {
        Payment saved = paymentRepository.save(mapToEntity(dto));
        return mapToDTO(saved);
    }
}