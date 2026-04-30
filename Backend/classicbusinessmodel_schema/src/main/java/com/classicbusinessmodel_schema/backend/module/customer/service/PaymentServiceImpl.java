package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// Marks this class as service layer
@Service
public class PaymentServiceImpl implements PaymentService {

    // Injects PaymentRepository
    @Autowired
    private PaymentRepository paymentRepository;

    // Convert Entity → DTO
    private PaymentResponseDTO mapToDTO(Payment payment) {

        return new PaymentResponseDTO(
                payment.getId().getCheckNumber(),
                payment.getCustomer().getCustomerNumber(),
                payment.getAmount(),
                payment.getPaymentDate()
        );
    }


    // Fetch all payments with pagination
    @Override
    public Page<PaymentResponseDTO> getAllPayments(Pageable pageable) {

        // Convert each entity to DTO
        Page<PaymentResponseDTO> page = paymentRepository.findAll(pageable)
                .map(this::mapToDTO);

        // Added exception if no data found
        if (page.isEmpty()) {
            throw new ResourceNotFoundException("No payments found");
        }

        return page;
    }

    // Fetch payments by customer
    @Override
    public List<PaymentResponseDTO> getPaymentsByCustomer(Integer customerNumber) {

        // Custom query method + mapping
        List<PaymentResponseDTO> list = paymentRepository.findByCustomerCustomerNumber(customerNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();

        // Added exception if no payments for customer
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No payments found for customer: " + customerNumber);
        }

        return list;


    }

    // Fetch payment by check number
    @Override
    public PaymentResponseDTO getPaymentByCheckNumber(String checkNumber) {

        // Fetch or throw exception
        Payment payment = paymentRepository.findByIdCheckNumber(checkNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with CheckNumber "+checkNumber));

        // Convert to DTO
        return mapToDTO(payment);
    }
}