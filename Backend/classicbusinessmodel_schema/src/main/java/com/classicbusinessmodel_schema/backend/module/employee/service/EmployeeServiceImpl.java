package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.EmployeeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        Employee emp = mapToEntity(dto);

        Employee saved = employeeRepository.save(emp);

        return mapToDTO(saved);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Integer id) {
        return mapToDTO(
                employeeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id))
        );
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Integer id, EmployeeRequestDTO dto) {

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setExtension(dto.getExtension());
        existing.setJobTitle(dto.getJobTitle());

        Office office = officeRepository.findById(dto.getOfficeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        existing.setOffice(office);

        if (dto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            existing.setManager(manager);
        }

        return mapToDTO(employeeRepository.save(existing));
    }

    @Override
    public List<EmployeeResponseDTO> getSubordinates(Integer managerId) {

        List<Employee> list = employeeRepository.findByManagerEmployeeNumber(managerId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No subordinates found");
        }

        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getManager(Integer employeeId) {

        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (emp.getManager() == null) {
            throw new ResourceNotFoundException("Manager not found");
        }

        return mapToDTO(emp.getManager());
    }

    private Employee mapToEntity(EmployeeRequestDTO dto) {

        Employee emp = new Employee();

        emp.setEmployeeNumber(dto.getEmployeeNumber());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmail(dto.getEmail());
        emp.setExtension(dto.getExtension());
        emp.setJobTitle(dto.getJobTitle());

        Office office = officeRepository.findById(dto.getOfficeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        emp.setOffice(office);

        if (dto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            emp.setManager(manager);
        }

        return emp;
    }

    private EmployeeResponseDTO mapToDTO(Employee emp) {

        return new EmployeeResponseDTO(
                emp.getEmployeeNumber(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getExtension(),
                emp.getEmail(),
                emp.getJobTitle(),
                emp.getOffice().getOfficeCode(),
                emp.getManager() != null ? emp.getManager().getEmployeeNumber() : null
        );
    }
}