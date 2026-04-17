package com.classicbusinessmodel_schema.backend.module.employee.controller;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.module.employee.dto.employeeDto.responseDto.EmployeeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.employeeDto.responseDto.EmployeeSimpleDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.employeeDto.requestDTO.EmployeeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO dto) {
        Employee employee = mapToEntity(dto);
        return mapToDTO(service.createEmployee(employee));
    }

    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees() {
        return service.getAllEmployees()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployee(@PathVariable Integer id) {
        return mapToDTO(service.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable Integer id,
                                              @RequestBody EmployeeRequestDTO dto) {
        Employee employee = mapToEntity(dto);
        return mapToDTO(service.updateEmployee(id, employee));
    }

    @GetMapping("/{id}/manager")
    public EmployeeSimpleDTO getManager(@PathVariable Integer id) {
        Employee manager = service.getManager(id);
        return mapToSimpleDTO(manager);
    }

    @GetMapping("/{id}/subordinates")
    public List<EmployeeSimpleDTO> getSubordinates(@PathVariable Integer id) {
        return service.getSubordinates(id)
                .stream()
                .map(this::mapToSimpleDTO)
                .collect(Collectors.toList());
    }

    private Employee mapToEntity(EmployeeRequestDTO dto) {
        Employee e = new Employee();
        e.setEmployeeNumber(dto.getEmployeeNumber());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setExtension(dto.getExtension());
        e.setJobTitle(dto.getJobTitle());

        if (dto.getOfficeCode() != null) {
            e.setOffice(new com.classicbusinessmodel_schema.backend.entity.Office());
            e.getOffice().setOfficeCode(dto.getOfficeCode());
        }

        if (dto.getManagerId() != null) {
            Employee manager = new Employee();
            manager.setEmployeeNumber(dto.getManagerId());
            e.setManager(manager);
        }

        return e;
    }

    private EmployeeResponseDTO mapToDTO(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeNumber(e.getEmployeeNumber());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setJobTitle(e.getJobTitle());
        dto.setOfficeCode(e.getOffice() != null ? e.getOffice().getOfficeCode() : null);
        dto.setManagerId(e.getManager() != null ? e.getManager().getEmployeeNumber() : null);
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