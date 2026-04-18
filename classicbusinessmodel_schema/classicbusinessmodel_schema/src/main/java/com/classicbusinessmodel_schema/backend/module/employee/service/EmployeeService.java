package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.EmployeeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeById(Integer id);

    EmployeeResponseDTO updateEmployee(Integer id, EmployeeRequestDTO dto);

    List<EmployeeResponseDTO> getSubordinates(Integer managerId);

    EmployeeResponseDTO getManager(Integer employeeId);
}