package com.classicbusinessmodel_schema.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PaymentId implements Serializable {

    @Column(name = "customer_number")
    private Integer customerNumber;

    @Column(name = "check_number")
    private String checkNumber;

    public PaymentId() {}

    public PaymentId(Integer customerNumber, String checkNumber) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentId)) return false;
        PaymentId that = (PaymentId) o;
        return Objects.equals(customerNumber, that.customerNumber)
                && Objects.equals(checkNumber, that.checkNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber, checkNumber);
    }
}