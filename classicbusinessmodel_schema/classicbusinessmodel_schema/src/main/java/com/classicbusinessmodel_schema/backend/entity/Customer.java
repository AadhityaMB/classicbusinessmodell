package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        @NotNull
        private Integer customerNumber;

        @NotBlank
        private String customerName;

        @NotBlank
        private String contactLastName;

        @NotBlank
        private String contactFirstName;

        @NotBlank
        private String phone;

        @NotBlank
        private String addressLine1;

        private String addressLine2;

        @NotBlank
        private String city;

        private String state;

        private String postalCode;

        @NotBlank
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

