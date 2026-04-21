package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByManagerEmployeeNumber(Integer managerId);

    List<Employee> findByOfficeOfficeCode(String officeCode);

    @Query("SELECT e FROM Employee e WHERE e.jobTitle = :jobTitle")
    List<Employee> findEmployeesByJobTitle(@Param("jobTitle") String jobTitle);

    @Query("SELECT e FROM Employee e WHERE e.manager.employeeNumber = :managerId")
    List<Employee> findSubordinatesCustom(@Param("managerId") Integer managerId);

    @Query("SELECT e.email FROM Employee e")
    List<String> findAllEmployeeEmails();

    @Query("SELECT e FROM Employee e WHERE e.employeeNumber = :id")
    Optional<Employee> findEmployeeByIdCustom(@Param("id") Integer id);

    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployeesCustom();

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.jobTitle = :jobTitle WHERE e.employeeNumber = :id")
    int updateEmployeeJobTitle(@Param("id") Integer id,
                               @Param("jobTitle") String jobTitle);

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.employeeNumber = :id")
    int deleteEmployeeByIdCustom(@Param("id") Integer id);
}