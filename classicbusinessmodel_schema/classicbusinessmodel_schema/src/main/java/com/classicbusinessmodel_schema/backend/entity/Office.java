package com.classicbusinessmodel_schema.backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "offices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Office {

        @Id
        private String officeCode;

        private String city;
        private String phone;
        private String addressLine1;
        private String addressLine2;
        private String state;
        private String country;
        private String postalCode;
        private String territory;

        @OneToMany(mappedBy = "office")
        private List<Employee> employees;
    }

