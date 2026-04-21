package com.classicbusinessmodel_schema.backend.module.customer.repository;

import com.classicbusinessmodel_schema.backend.entity.Payment;
import com.classicbusinessmodel_schema.backend.entity.PaymentId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    // 1. TEST PAYMENT ENTITY CREATION
    @Test
    void testPaymentObjectCreation() {
        PaymentId id = new PaymentId(1, "CHK1");

        Payment payment = new Payment();
        payment.setId(id);
        payment.setAmount(BigDecimal.valueOf(1000));
        payment.setPaymentDate(LocalDate.of(2024, 1, 1));

        assertEquals(id, payment.getId());
        assertEquals(BigDecimal.valueOf(1000), payment.getAmount());
        assertEquals(LocalDate.of(2024, 1, 1), payment.getPaymentDate());
    }

    // 2. TEST PAYMENT ID EQUALITY
    @Test
    void testPaymentIdEquals() {
        PaymentId id1 = new PaymentId(1, "CHK1");
        PaymentId id2 = new PaymentId(1, "CHK1");

        assertEquals(id1, id2);
    }

    // 3. TEST PAYMENT ID HASHCODE
    @Test
    void testPaymentIdHashCode() {
        PaymentId id1 = new PaymentId(1, "CHK1");
        PaymentId id2 = new PaymentId(1, "CHK1");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    // 4. TEST PAYMENT SETTERS
    @Test
    void testPaymentSetters() {
        Payment payment = new Payment();

        payment.setAmount(BigDecimal.valueOf(2000));
        payment.setPaymentDate(LocalDate.now());

        assertEquals(BigDecimal.valueOf(2000), payment.getAmount());
        assertNotNull(payment.getPaymentDate());
    }

    // 5. TEST NULL VALUES HANDLING
    @Test
    void testNullValues() {
        Payment payment = new Payment();

        assertNull(payment.getId());
        assertNull(payment.getCustomer());
        assertNull(payment.getAmount());
    }
}