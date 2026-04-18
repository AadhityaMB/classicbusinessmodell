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

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {

        Office office = new Office();

        office.setOfficeCode(dto.getOfficeCode());
        office.setCity(dto.getCity());
        office.setPhone(dto.getPhone());
        office.setAddressLine1(dto.getAddressLine1());
        office.setAddressLine2(dto.getAddressLine2());
        office.setState(dto.getState());
        office.setCountry(dto.getCountry());
        office.setPostalCode(dto.getPostalCode());
        office.setTerritory(dto.getTerritory());

        return mapToDTO(officeRepository.save(office));
    }

    @Override
    public List<OfficeResponseDTO> getAllOffices() {
        return officeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OfficeResponseDTO getOfficeByCode(String officeCode) {

        Office office = officeRepository.findById(officeCode)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with code: " + officeCode));

        return mapToDTO(office);
    }

    @Override
    public List<EmployeeResponseDTO> getEmployeesByOffice(String officeCode) {

        Office office = officeRepository.findById(officeCode)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        List<Employee> employees = employeeRepository.findByOfficeOfficeCode(officeCode);

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found for office: " + officeCode);
        }

        return employees.stream()
                .map(this::mapEmployeeToDTO)
                .collect(Collectors.toList());
    }

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

    private EmployeeResponseDTO mapEmployeeToDTO(Employee emp) {

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