package com.classicbusinessmodel_schema.backend.entity;



import java.io.Serializable;
import java.util.Objects;


public class PaymentId implements Serializable {

    private Integer customer;
    private String checkNumber;

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public PaymentId() {
    }

    public PaymentId(Integer customer, String checkNumber) {
        this.customer = customer;
        this.checkNumber = checkNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId paymentId = (PaymentId) o;
        return Objects.equals(customer, paymentId.customer) && Objects.equals(checkNumber, paymentId.checkNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, checkNumber);
    }
}