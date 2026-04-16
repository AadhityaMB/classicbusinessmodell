package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // EXISTING
    List<Employee> findByManagerEmployeeNumber(Integer managerId);
    List<Employee> findByOfficeOfficeCode(String officeCode);

    // ✅ CUSTOM QUERY 1
    @Query("SELECT e FROM Employee e WHERE e.jobTitle = :jobTitle")
    List<Employee> findEmployeesByJobTitle(String jobTitle);

    // ✅ CUSTOM QUERY 2
    @Query("SELECT e FROM Employee e WHERE e.manager.employeeNumber = :managerId")
    List<Employee> findSubordinatesCustom(Integer managerId);

    // ✅ CUSTOM QUERY 3
    @Query("SELECT e.email FROM Employee e")
    List<String> findAllEmployeeEmails();
}