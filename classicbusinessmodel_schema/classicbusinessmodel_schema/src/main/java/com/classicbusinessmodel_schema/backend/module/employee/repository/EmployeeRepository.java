package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Find subordinates
    List<Employee> findByManagerEmployeeNumber(Integer managerId);

    // Find by office
    List<Employee> findByOfficeOfficeCode(String officeCode);
}