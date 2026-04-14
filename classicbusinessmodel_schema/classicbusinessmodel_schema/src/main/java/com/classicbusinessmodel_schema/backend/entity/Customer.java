package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

        @Id
        private Integer customerNumber;

        private String customerName;
        private String contactLastName;
        private String contactFirstName;
        private String phone;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private Double creditLimit;

        /*
        Many customers handled by one employee
        */
        @ManyToOne
        @JoinColumn(name = "salesRepEmployeeNumber")
        private Employee salesRepEmployee;
        /*
         One customer many orders
         */
        @OneToMany(mappedBy = "customer")
        private List<Orders> orders;

        /*
        One customer many payments
        */
        @OneToMany(mappedBy = "customer")
        private List<Payment> payments;
}

