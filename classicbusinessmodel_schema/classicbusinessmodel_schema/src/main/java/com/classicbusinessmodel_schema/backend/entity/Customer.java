package com.classicbusinessmodel_schema.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Table(name = "customers")

public class Customer {

        @Id
        @Column(name = "customerNumber")
        private Integer customerNumber;

        @Column(name = "customerName", nullable = false)
        private String customerName;

        @Column(name = "contactLastName", nullable = false)
        private String contactLastName;

        @Column(name = "contactFirstName", nullable = false)
        private String contactFirstName;

        private String phone;

        private String addressLine1;

        private String addressLine2;

        private String city;

        private String state;

        private String postalCode;

        private String country;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "salesRepEmployeeNumber")
        private Employee salesRep;

        @DecimalMin("0.0")
        private BigDecimal creditLimit;

        @JsonIgnore
        @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
        private List<Orders> orders;

        @JsonIgnore
        @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
        private List<Payment> payments;

        public String getAddressLine1() {
                return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
                this.addressLine1 = addressLine1;
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

        public String getPostalCode() {
                return postalCode;
        }

        public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
        }

        public String getCountry() {
                return country;
        }

        public void setCountry(String country) {
                this.country = country;
        }

        public Employee getSalesRep() {
                return salesRep;
        }

        public void setSalesRep(Employee salesRep) {
                this.salesRep = salesRep;
        }

        public BigDecimal getCreditLimit() {
                return creditLimit;
        }

        public void setCreditLimit(BigDecimal creditLimit) {
                this.creditLimit = creditLimit;
        }

        public List<Orders> getOrders() {
                return orders;
        }

        public void setOrders(List<Orders> orders) {
                this.orders = orders;
        }

        public List<Payment> getPayments() {
                return payments;
        }

        public void setPayments(List<Payment> payments) {
                this.payments = payments;
        }

        public Customer() {
        }

        public Customer(Integer customerNumber, String customerName, String contactLastName, String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state, String postalCode, String country, Employee salesRep, BigDecimal creditLimit, List<Orders> orders, List<Payment> payments) {
                this.customerNumber = customerNumber;
                this.customerName = customerName;
                this.contactLastName = contactLastName;
                this.contactFirstName = contactFirstName;
                this.phone = phone;
                this.addressLine1 = addressLine1;
                this.addressLine2 = addressLine2;
                this.city = city;
                this.state = state;
                this.postalCode = postalCode;
                this.country = country;
                this.salesRep = salesRep;
                this.creditLimit = creditLimit;
                this.orders = orders;
                this.payments = payments;
        }
}