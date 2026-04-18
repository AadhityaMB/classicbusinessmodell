package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.EmployeeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.repository.EmployeeRepository;
import com.classicbusinessmodel_schema.backend.module.employee.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Office createOffice() {
        Office o = new Office();
        o.setOfficeCode("1");
        return o;
    }

    private Employee createEmployee() {
        Employee e = new Employee();
        e.setEmployeeNumber(1);
        e.setFirstName("Test");
        e.setLastName("User");
        e.setEmail("test@mail.com");
        e.setExtension("x123");
        e.setJobTitle("Developer");
        e.setOffice(createOffice()); // 🔥 IMPORTANT FIX
        return e;
    }

    @Test
    void testCreateEmployee() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setOfficeCode("1");

        when(officeRepository.findById("1")).thenReturn(Optional.of(createOffice()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(createEmployee());

        assertNotNull(employeeService.createEmployee(dto));
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(createEmployee()));

        assertNotNull(employeeService.getEmployeeById(1));
    }

    @Test
    void testGetSubordinates() {
        when(employeeRepository.findByManagerEmployeeNumber(1))
                .thenReturn(List.of(createEmployee()));

        assertEquals(1, employeeService.getSubordinates(1).size());
    }

    @Test
    void testGetEmployeeNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> employeeService.getEmployeeById(1));
    }

    @Test
    void testGetSubordinatesEmpty() {
        when(employeeRepository.findByManagerEmployeeNumber(1))
                .thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> employeeService.getSubordinates(1));
    }
}