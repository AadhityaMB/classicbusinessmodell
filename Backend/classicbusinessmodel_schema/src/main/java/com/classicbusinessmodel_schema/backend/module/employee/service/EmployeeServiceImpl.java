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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

// Marks this class as a service layer component
@Service
public class EmployeeServiceImpl implements EmployeeService {

    // Inject Employee repository
    @Autowired
    private EmployeeRepository employeeRepository;

    // Inject Office repository
    @Autowired
    private OfficeRepository officeRepository;

    // Create a new employee
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        // Convert DTO into entity
        Employee emp = mapToEntity(dto);

        // Save employee to database
        Employee saved = employeeRepository.save(emp);

        // Convert saved entity back to response DTO
        return mapToDTO(saved);
    }

    // Get all employees with pagination
    @Override
    public Page<EmployeeResponseDTO> getAllEmployees(int page, int size) {

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Fetch employees and convert each entity to DTO
        return employeeRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // Get employee by ID
    @Override
    public EmployeeResponseDTO getEmployeeById(Integer id) {

        return mapToDTO(
                employeeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Employee not found with ID: " + id))
        );
    }

    // Update existing employee
    @Override
    public EmployeeResponseDTO updateEmployee(Integer id, EmployeeRequestDTO dto) {

        // Find employee or throw exception
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + id));

        // Update employee basic details
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setExtension(dto.getExtension());
        existing.setJobTitle(dto.getJobTitle());

        // Fetch and set office
        Office office = officeRepository.findById(dto.getOfficeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        existing.setOffice(office);

        // Set manager if provided
        if (dto.getManagerId() != null) {

            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

            existing.setManager(manager);
        }

        // Save updated employee
        return mapToDTO(employeeRepository.save(existing));
    }

    // Get employees reporting to a manager
    @Override
    public List<EmployeeResponseDTO> getSubordinates(Integer managerId) {

        // Fetch subordinates
        List<Employee> list =
                employeeRepository.findByManagerEmployeeNumber(managerId);

        // Throw exception if none found
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No subordinates found");
        }

        // Convert entity list to DTO list
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get manager of an employee
    @Override
    public EmployeeResponseDTO getManager(Integer employeeId) {

        // Find employee
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found"));

        // Check if manager exists
        if (emp.getManager() == null) {
            throw new ResourceNotFoundException("Manager not found");
        }

        // Return manager details
        return mapToDTO(emp.getManager());
    }

    // Convert request DTO to Employee entity
    private Employee mapToEntity(EmployeeRequestDTO dto) {

        Employee emp = new Employee();

        // Set employee details
        emp.setEmployeeNumber(dto.getEmployeeNumber());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmail(dto.getEmail());
        emp.setExtension(dto.getExtension());
        emp.setJobTitle(dto.getJobTitle());

        // Fetch and assign office
        Office office = officeRepository.findById(dto.getOfficeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        emp.setOffice(office);

        // Assign manager if provided
        if (dto.getManagerId() != null) {

            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

            emp.setManager(manager);
        }

        return emp;
    }

    // Convert Employee entity to response DTO
    private EmployeeResponseDTO mapToDTO(Employee emp) {

        return new EmployeeResponseDTO(
                emp.getEmployeeNumber(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getExtension(),
                emp.getEmail(),
                emp.getJobTitle(),
                emp.getOffice().getOfficeCode(),

                // Return manager id if manager exists
                emp.getManager() != null
                        ? emp.getManager().getEmployeeNumber()
                        : null
        );
    }
}