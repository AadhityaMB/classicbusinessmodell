package com.classicbusinessmodel_schema.backend.entity;


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
public class Customer {

        @Id
        @Column(name = "customernumber")
        private Integer customerNumber;

        @Column(name = "customername", length = 50, nullable = false)
        @NotBlank(message = "Customer name is required")
        @Size(max = 50)
        private String customerName;

        @Column(name = "contactlastname", length = 50, nullable = false)
        @NotBlank(message = "Contact last name is required")
        @Size(max = 50)
        private String contactLastName;

        @Column(name = "contactfirstname", length = 50, nullable = false)
        @NotBlank(message = "Contact first name is required")
        @Size(max = 50)
        private String contactFirstName;

        @Column(name = "phone", length = 50, nullable = false)
        @NotBlank(message = "Phone is required")
        @Size(max = 50)
        private String phone;

        @Column(name = "addressline1", length = 50, nullable = false)
        @NotBlank(message = "Address line 1 is required")
        @Size(max = 50)
        private String addressLine1;

        @Column(name = "addressline2", length = 50)
        @Size(max = 50)
        private String addressLine2;

        @Column(name = "city", length = 50, nullable = false)
        @NotBlank(message = "City is required")
        @Size(max = 50)
        private String city;

        @Column(name = "state", length = 50)
        @Size(max = 50)
        private String state;

        @Column(name = "postalcode", length = 15)
        @Size(max = 15)
        private String postalCode;

        @Column(name = "country", length = 50, nullable = false)
        @NotBlank(message = "Country is required")
        @Size(max = 50)
        private String country;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "salesrepemployeenumber")
        @NotNull(message = "Sales representative is required")

        private Employee salesRepEmployee;

        @Column(name = "creditlimit", precision = 10, scale = 2)
        @DecimalMin(value = "0.0", message = "Credit limit cannot be negative")
        private BigDecimal creditLimit;

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Orders> orders = new ArrayList<>();

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Payment> payments = new ArrayList<>();
}
