package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Override
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    // READ ALL
    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // READ BY ID
    @Override
    public Employee getEmployeeById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    // UPDATE
    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existing = getEmployeeById(id);

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        existing.setJobTitle(employee.getJobTitle());

        return repository.save(existing);
    }

    // GET SUBORDINATES
    @Override
    public List<Employee> getSubordinates(Integer managerId) {
        return repository.findByManagerEmployeeNumber(managerId);
    }

    // GET MANAGER
    @Override
    public Employee getManager(Integer employeeId) {
        Employee employee = getEmployeeById(employeeId);

        if (employee.getManager() == null) {
            throw new ResourceNotFoundException("Manager not found");
        }

        return employee.getManager();
    }
}
