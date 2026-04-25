package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.OfficeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.OfficeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Marks this class as a service layer component
@Service
public class OfficeServiceImpl implements OfficeService {

    // Inject Office repository
    @Autowired
    private OfficeRepository officeRepository;

    // Inject Employee repository
    @Autowired
    private EmployeeRepository employeeRepository;

    // Create a new office
    @Override
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {

        // Create office entity
        Office office = new Office();

        // Set office details from request DTO
        office.setOfficeCode(dto.getOfficeCode());
        office.setCity(dto.getCity());
        office.setPhone(dto.getPhone());
        office.setAddressLine1(dto.getAddressLine1());
        office.setAddressLine2(dto.getAddressLine2());
        office.setState(dto.getState());
        office.setCountry(dto.getCountry());
        office.setPostalCode(dto.getPostalCode());
        office.setTerritory(dto.getTerritory());

        // Save office and return response DTO
        return mapToDTO(officeRepository.save(office));
    }

    // Fetch all offices
    @Override
    public List<OfficeResponseDTO> getAllOffices() {

        // Get all offices and convert entities to DTOs
        return officeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Fetch office by office code
    @Override
    public OfficeResponseDTO getOfficeByCode(String officeCode) {

        // Find office or throw exception if not found
        Office office = officeRepository.findById(officeCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Office not found with code: " + officeCode
                        )
                );

        // Convert entity to response DTO
        return mapToDTO(office);
    }

    // Get all employees working in a specific office
    @Override
    public List<EmployeeResponseDTO> getEmployeesByOffice(String officeCode) {

        // Check whether office exists
        Office office = officeRepository.findById(officeCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found")
                );

        // Fetch employees belonging to that office
        List<Employee> employees =
                employeeRepository.findByOfficeOfficeCode(officeCode);

        // Throw exception if no employees found
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No employees found for office: " + officeCode
            );
        }

        // Convert employee entities into DTOs
        return employees.stream()
                .map(this::mapEmployeeToDTO)
                .collect(Collectors.toList());
    }

    // Convert Office entity to response DTO
    private OfficeResponseDTO mapToDTO(Office office) {

        return new OfficeResponseDTO(
                office.getOfficeCode(),
                office.getCity(),
                office.getPhone(),
                office.getAddressLine1(),
                office.getAddressLine2(),
                office.getState(),
                office.getCountry(),
                office.getPostalCode(),
                office.getTerritory()
        );
    }

    // Convert Employee entity to response DTO
    private EmployeeResponseDTO mapEmployeeToDTO(Employee emp) {

        return new EmployeeResponseDTO(
                emp.getEmployeeNumber(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getExtension(),
                emp.getEmail(),
                emp.getJobTitle(),
                emp.getOffice().getOfficeCode(),

                // Return manager ID if manager exists
                emp.getManager() != null
                        ? emp.getManager().getEmployeeNumber()
                        : null
        );
    }
}