package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;

import java.util.List;

public interface OfficeService {

    List<Office> getAllOffices();

    Office getOfficeByCode(String officeCode);

    List<Employee> getEmployeesByOffice(String officeCode);
}