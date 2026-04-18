package com.classicbusinessmodel_schema.backend.module.customer.service;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.customer.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testGetPaymentsByCustomer() {
        when(paymentRepository.findByCustomerCustomerNumber(1))
                .thenReturn(List.of(new Payment()));

        var result = paymentService.getPaymentsByCustomer(1);

        assertEquals(1, result.size());
    }

    @Test
    void testGetPaymentsByDateRange() {
        when(paymentRepository.findByPaymentDateBetween(any(), any()))
                .thenReturn(List.of(new Payment()));

        var result = paymentService.getPaymentsByDateRange(
                LocalDate.now().minusDays(1),
                LocalDate.now()
        );

        assertEquals(1, result.size());
    }

    @Test
    void testUpdatePaymentSuccess() {
        PaymentId id = new PaymentId(1, "CHK1");

        doReturn(true).when(paymentRepository).existsById(id);

        doReturn(1).when(paymentRepository)
                .updatePaymentAmount(1, "CHK1", BigDecimal.valueOf(1000));

        paymentService.updatePaymentAmount(1, "CHK1", BigDecimal.valueOf(1000));

        verify(paymentRepository)
                .updatePaymentAmount(1, "CHK1", BigDecimal.valueOf(1000));
    }

    @Test
    void testUpdatePaymentNotFound() {
        PaymentId id = new PaymentId(1, "CHK1");

        when(paymentRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.updatePaymentAmount(1, "CHK1", BigDecimal.TEN));
    }

    @Test
    void testDeletePayment() {
        PaymentId id = new PaymentId(1, "CHK1");

        when(paymentRepository.existsById(id)).thenReturn(true);
        doNothing().when(paymentRepository).deletePayment(1, "CHK1");

        paymentService.deletePayment(1, "CHK1");

        verify(paymentRepository).deletePayment(1, "CHK1");
    }
}