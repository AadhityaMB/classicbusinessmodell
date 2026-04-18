package com.classicbusinessmodel_schema.backend.module.customer.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponseDTO {

    private String checkNumber;
    private Integer customerNumber;
    private BigDecimal amount;
    private LocalDate paymentDate;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(String checkNumber, Integer customerNumber, BigDecimal amount, LocalDate paymentDate) {
        this.checkNumber = checkNumber;
        this.customerNumber = customerNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // getters & setters
    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}