package com.classicbusinessmodel_schema.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

        @Id
        @Column(name = "customerNumber")
        private Integer customerNumber;

        @NotBlank
        @Size(max = 50)
        @Column(name = "customerName", nullable = false)
        private String customerName;

        @NotBlank
        @Size(max = 50)
        @Column(name = "contactLastName", nullable = false)
        private String contactLastName;

        @NotBlank
        @Size(max = 50)
        @Column(name = "contactFirstName", nullable = false)
        private String contactFirstName;

        @NotBlank
        @Size(max = 50)
        private String phone;

        @NotBlank
        @Size(max = 50)
        private String addressLine1;

        @Size(max = 50)
        private String addressLine2;

        @NotBlank
        @Size(max = 50)
        private String city;

        @Size(max = 50)
        private String state;

        @Size(max = 15)
        private String postalCode;

        @NotBlank
        @Size(max = 50)
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
}