package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.exception.InvalidDataException;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.EmployeeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeRepository officeRepository;

    // Create a new employee
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        // ResourceAlreadyExistsException: Check if employee already exists
        if (employeeRepository.existsById(dto.getEmployeeNumber())) {
            throw new ResourceAlreadyExistsException(
                    "Employee already exists with ID: " + dto.getEmployeeNumber());
        }

        Employee emp = mapToEntity(dto);
        return mapToDTO(employeeRepository.save(emp));
    }

    // Get all employees with pagination
    @Override
    public Page<EmployeeResponseDTO> getAllEmployees(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Get employee by ID
    @Override
    public EmployeeResponseDTO getEmployeeById(Integer id) {

        // ResourceNotFoundException: Throw if employee not found
        return mapToDTO(
                employeeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Employee not found with ID: " + id))
        );
    }

    // Update existing employee
    @Override
    public EmployeeResponseDTO updateEmployee(Integer id, EmployeeRequestDTO dto) {

        // InvalidDataException: Prevent employee from being their own manager
        if (dto.getManagerId() != null && dto.getManagerId().equals(id)) {
            throw new InvalidDataException("Employee cannot be assigned as their own manager");
        }

        // ResourceNotFoundException: Find employee or throw
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setExtension(dto.getExtension());
        existing.setJobTitle(dto.getJobTitle());

        // ResourceNotFoundException: Fetch and set office
        Office office = officeRepository.findById(dto.getOfficeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with code : " + dto.getOfficeCode()));
        existing.setOffice(office);

        // Set manager if provided
        if (dto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with ID : " + dto.getManagerId()));
            existing.setManager(manager);
        }

        return mapToDTO(employeeRepository.save(existing));
    }

    // Get subordinates of a manager
    @Override
    public List<EmployeeResponseDTO> getSubordinates(Integer managerId) {

        // ResourceNotFoundException: Check if manager exists
        employeeRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Manager not found with ID: " + managerId));

        List<Employee> list = employeeRepository.findByManagerEmployeeNumber(managerId);

        // ResourceNotFoundException: No subordinates found
        if (list.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No subordinates found for manager ID: " + managerId);
        }

        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get manager of an employee
    @Override
    public EmployeeResponseDTO getManager(Integer employeeId) {

        // ResourceNotFoundException: Find employee
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + employeeId));

        // ResourceNotFoundException: Check manager is assigned
        if (emp.getManager() == null) {
            throw new ResourceNotFoundException(
                    "No manager assigned to employee with ID: " + employeeId);
        }

        return mapToDTO(emp.getManager());
    }

    // Convert DTO to Entity
    private Employee mapToEntity(EmployeeRequestDTO dto) {

        Employee emp = new Employee();
        emp.setEmployeeNumber(dto.getEmployeeNumber());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmail(dto.getEmail());
        emp.setExtension(dto.getExtension());
        emp.setJobTitle(dto.getJobTitle());

        // ResourceNotFoundException: Office must exist
        Office office = officeRepository.findById(dto.getOfficeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with code : " + dto.getOfficeCode()));
        emp.setOffice(office);

        if (dto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with ID : " + dto.getManagerId()));
            emp.setManager(manager);
        }

        return emp;
    }

    // Convert Entity to Response DTO
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