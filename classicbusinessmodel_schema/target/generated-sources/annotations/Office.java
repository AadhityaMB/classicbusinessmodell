package com.classicbusinessmodel_schema.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
public class Office {

        @Id
        @Column(name = "officeCode")
        private String officeCode;

        @NotBlank
        @Size(max = 50)
        private String city;

        @NotBlank
        @Size(max = 50)
        private String phone;

        @NotBlank
        @Size(max = 50)
        private String addressLine1;

        @Size(max = 50)
        private String addressLine2;

        @Size(max = 50)
        private String state;

        @NotBlank
        @Size(max = 50)
        private String country;

        @NotBlank
        @Size(max = 15)
        private String postalCode;

        @NotBlank
        @Size(max = 10)
        private String territory;

        @JsonIgnore
        @OneToMany(mappedBy = "office", fetch = FetchType.LAZY)
        private List<Employee> employees;
}