package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
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
    private Integer employeeNumber;

    private String lastName;

    private String firstName;

    private String extension;

    private String email;

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