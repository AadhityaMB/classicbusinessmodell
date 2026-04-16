package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;

    public OfficeServiceImpl(OfficeRepository officeRepository,
                             EmployeeRepository employeeRepository) {
        this.officeRepository = officeRepository;
        this.employeeRepository = employeeRepository;
    }

    // GET ALL OFFICES
    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    // GET OFFICE BY CODE
    @Override
    public Office getOfficeByCode(String officeCode) {
        return officeRepository.findById(officeCode)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
    }

    // GET EMPLOYEES IN OFFICE
    @Override
    public List<Employee> getEmployeesByOffice(String officeCode) {
        return employeeRepository.findByOfficeOfficeCode(officeCode);
    }
}