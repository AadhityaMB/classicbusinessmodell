package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "payments")
public class Payment {

        @EmbeddedId
        private PaymentId id;

        @MapsId("customerNumber")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_number")
        private Customer customer;


        @NotNull
        private LocalDate paymentDate;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal amount;


        public Payment(PaymentId id, Customer customer, LocalDate paymentDate, BigDecimal amount) {
                this.id = id;
                this.customer = customer;
                this.paymentDate = paymentDate;
                this.amount = amount;
        }
        public Payment() {
        }

        public PaymentId getId() {
                return id;
        }

        public void setId(PaymentId id) {
                this.id = id;
        }

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        public LocalDate getPaymentDate() {
                return paymentDate;
        }

        public void setPaymentDate(LocalDate paymentDate) {
                this.paymentDate = paymentDate;
        }

        public BigDecimal getAmount() {
                return amount;
        }

        public void setAmount(BigDecimal amount) {
                this.amount = amount;
        }
}