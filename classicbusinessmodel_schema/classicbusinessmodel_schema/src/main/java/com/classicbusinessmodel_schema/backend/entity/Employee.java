package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "employeenumber")
    @NotNull(message = "Employee number is required")
    private Integer employeeNumber;

    @Column(name = "lastname", length = 50, nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;

    @Column(name = "firstname", length = 50, nullable = false)
    @NotBlank(message = "First name is required")
    @Size(max = 50)
    private String firstName;

    @Column(name = "extension", length = 10, nullable = false)
    @NotBlank(message = "Extension is required")
    @Size(max = 10)
    private String extension;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officecode", nullable = false)
    @NotNull(message = "Office is required")
    private Office office;

    // Self-referencing manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportsto")
    private Employee reportsTo;

    @OneToMany(mappedBy = "reportsTo", fetch = FetchType.LAZY)
    private List<Employee> subordinates = new ArrayList<>();

    @Column(name = "jobtitle", length = 50, nullable = false)
    @NotBlank(message = "Job title is required")
    @Size(max = 50)
    private String jobTitle;

    @OneToMany(mappedBy = "salesRepEmployee", fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();
}
