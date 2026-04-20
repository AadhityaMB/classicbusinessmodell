package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import com.classicbusinessmodel_schema.backend.module.customer.dto.response.PaymentResponseDTO;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    // helper entity
    private Payment getPayment() {
        Payment payment = new Payment();

        PaymentId id = new PaymentId();
        id.setCustomerNumber(1);
        id.setCheckNumber("CHK1");

        Customer customer = new Customer();
        customer.setCustomerNumber(1);

        payment.setId(id);
        payment.setCustomer(customer); // ⭐ IMPORTANT FIX
        payment.setAmount(BigDecimal.valueOf(1000));
        payment.setPaymentDate(LocalDate.now());

        return payment;
    }

    // 1. GET ALL PAYMENTS
    @Test
    void testGetAllPayments() {

        when(paymentRepository.findAll())
                .thenReturn(List.of(getPayment()));

        List<PaymentResponseDTO> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
        assertEquals("CHK1", result.get(0).getCheckNumber());
    }

    // 2. GET BY CUSTOMER
    @Test
    void testGetPaymentsByCustomer() {

        when(paymentRepository.findByCustomerCustomerNumber(1))
                .thenReturn(List.of(getPayment()));

        List<PaymentResponseDTO> result = paymentService.getPaymentsByCustomer(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getCustomerNumber());
    }

    // 3. GET BY CHECK NUMBER - SUCCESS
    @Test
    void testGetPaymentByCheckNumber_Success() {

        when(paymentRepository.findByIdCheckNumber("CHK1"))
                .thenReturn(Optional.of(getPayment()));

        PaymentResponseDTO result = paymentService.getPaymentByCheckNumber("CHK1");

        assertNotNull(result);
        assertEquals("CHK1", result.getCheckNumber());
    }

    // 4. GET BY CHECK NUMBER - NOT FOUND
    @Test
    void testGetPaymentByCheckNumber_NotFound() {

        when(paymentRepository.findByIdCheckNumber("CHK1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.getPaymentByCheckNumber("CHK1"));
    }

    // 5. CREATE PAYMENT
    @Test
    void testCreatePayment() {

        Payment payment = getPayment();

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        PaymentResponseDTO result = paymentService.createPayment(
                new PaymentResponseDTO("CHK1", 1, BigDecimal.valueOf(1000), LocalDate.now())
        );

        assertNotNull(result);
        assertEquals("CHK1", result.getCheckNumber());
    }
}