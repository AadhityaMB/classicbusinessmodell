package com.classicbusinessmodel_schema.backend.module.customer.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CustomerRequestDTO {

    @NotNull(message = "Customer number is required")
    private Integer customerNumber;

    @NotBlank(message = "Customer name is required")
    @Size(max = 50)
    private String customerName;

    @NotBlank(message = "Contact last name is required")
    @Size(max = 50)
    private String contactLastName;

    @NotBlank(message = "Contact first name is required")
    @Size(max = 50)
    private String contactFirstName;

    @NotBlank(message = "Phone is required")
    @Size(max = 50)
    private String phone;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 50)
    private String addressLine1;

    private String addressLine2; // optional

    @NotBlank(message = "City is required")
    @Size(max = 50)
    private String city;

    private String state; // optional

    private String postalCode; // optional

    @NotBlank(message = "Country is required")
    @Size(max = 50)
    private String country;

    @DecimalMin(value = "0.0", message = "Credit limit cannot be negative")
    private BigDecimal creditLimit; // optional

    // Only ID of sales rep (not full object)
    private Integer salesRepEmployeeNumber; // optional

    public CustomerRequestDTO() {
    }

    public CustomerRequestDTO(String addressLine1, Integer customerNumber, String customerName, String contactLastName, String contactFirstName, String phone, String addressLine2, String city, String state, String postalCode, String country, BigDecimal creditLimit, Integer salesRepEmployeeNumber) {
        this.addressLine1 = addressLine1;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.contactLastName = contactLastName;
        this.contactFirstName = contactFirstName;
        this.phone = phone;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.creditLimit = creditLimit;
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getSalesRepEmployeeNumber() {
        return salesRepEmployeeNumber;
    }

    public void setSalesRepEmployeeNumber(Integer salesRepEmployeeNumber) {
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
    }
}