package com.classicbusinessmodel_schema.backend.module.employee.controller;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.module.employee.dto.employeeDto.responseDto.EmployeeSimpleDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.officedto.OfficeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.service.OfficeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    private final OfficeService service;

    public OfficeController(OfficeService service) {
        this.service = service;
    }

    @GetMapping
    public List<OfficeResponseDTO> getAllOffices() {
        return service.getAllOffices()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    public OfficeResponseDTO getOffice(@PathVariable String code) {
        return mapToDTO(service.getOfficeByCode(code));
    }

    @GetMapping("/{code}/employees")
    public List<EmployeeSimpleDTO> getEmployees(@PathVariable String code) {
        List<Employee> employees = service.getEmployeesByOffice(code);

        return employees.stream()
                .map(this::mapToSimpleDTO)
                .collect(Collectors.toList());
    }

    private OfficeResponseDTO mapToDTO(Office o) {
        OfficeResponseDTO dto = new OfficeResponseDTO();
        dto.setOfficeCode(o.getOfficeCode());
        dto.setCity(o.getCity());
        dto.setPhone(o.getPhone());
        dto.setCountry(o.getCountry());
        return dto;
    }

    private EmployeeSimpleDTO mapToSimpleDTO(Employee e) {
        EmployeeSimpleDTO dto = new EmployeeSimpleDTO();
        dto.setEmployeeNumber(e.getEmployeeNumber());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        return dto;
    }
}