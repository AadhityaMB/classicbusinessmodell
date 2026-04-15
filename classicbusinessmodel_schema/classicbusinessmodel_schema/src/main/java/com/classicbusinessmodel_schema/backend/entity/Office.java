package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Office {

        @Id
        @Column(name = "officecode", length = 10)
        @NotBlank(message = "Office code is required")
        @Size(max = 10)
        private String officeCode;

        @Column(name = "city", length = 50, nullable = false)
        @NotBlank(message = "City is required")
        @Size(max = 50)
        private String city;

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

        @Column(name = "state", length = 50)
        @Size(max = 50)
        private String state;

        @Column(name = "country", length = 50, nullable = false)
        @NotBlank(message = "Country is required")
        @Size(max = 50)
        private String country;

        @Column(name = "postalcode", length = 15, nullable = false)
        @NotBlank(message = "Postal code is required")
        @Size(max = 15)
        private String postalCode;

        @Column(name = "territory", length = 10, nullable = false)
        @NotBlank(message = "Territory is required")
        @Size(max = 10)
        private String territory;

        @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Employee> employees = new ArrayList<>();
}
