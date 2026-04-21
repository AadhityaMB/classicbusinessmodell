package com.classicbusinessmodel_schema.backend.module.customer.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentRequestDTO {

    @NotNull
    private Integer customerNumber;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    public PaymentRequestDTO() {
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
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

    public PaymentRequestDTO(Integer customerNumber, LocalDate paymentDate, BigDecimal amount) {
        this.customerNumber = customerNumber;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
}