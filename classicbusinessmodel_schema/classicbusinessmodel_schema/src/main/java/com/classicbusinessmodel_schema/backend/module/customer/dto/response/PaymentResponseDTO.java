package com.classicbusinessmodel_schema.backend.module.customer.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponseDTO {

    private Integer customerNumber;
    private String checkNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(Integer customerNumber, String checkNumber,
                              LocalDate paymentDate, BigDecimal amount) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
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
}