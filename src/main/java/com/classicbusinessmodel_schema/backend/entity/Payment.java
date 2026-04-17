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
@IdClass(PaymentId.class)

public class Payment {

        @Id
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customerNumber")
        private Customer customer;

        @Id
        @NotBlank
        @Size(max = 50)
        private String checkNumber;

        @NotNull
        private LocalDate paymentDate;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal amount;

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        public String getCheckNumber() {
                return checkNumber;
        }

        public void setCheckNumber(String checkNumber) {
                this.checkNumber = checkNumber;
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

        public Payment() {
        }

        public Payment(Customer customer, String checkNumber, LocalDate paymentDate, BigDecimal amount) {
                this.customer = customer;
                this.checkNumber = checkNumber;
                this.paymentDate = paymentDate;
                this.amount = amount;
        }
}