package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @NotNull
    private Integer employeeNumber;

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String extension;

    @NotBlank
    private String email;

    @NotBlank
    private String jobTitle;

    /*
     Many employees belong to one office
     */
    @ManyToOne
    @JoinColumn(name = "officeCode")
    private Office office;

    /*
     Self relationship (manager)
     */
    @ManyToOne
    @JoinColumn(name = "reportsTo")
    private Employee manager;

    /*
     Employees under this manager
     */
    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates;

    /*
     One employee handles many customers
     */
    @OneToMany(mappedBy = "salesRepEmployee")
    private List<Customer> customers;
}