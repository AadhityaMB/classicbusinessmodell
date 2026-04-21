package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.OfficeRequestDTO;
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
class OfficeServiceTest {

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private OfficeServiceImpl officeService;

    private Office createOffice() {
        Office o = new Office();
        o.setOfficeCode("1");
        o.setCity("Chennai");
        o.setPhone("123");
        o.setAddressLine1("Street");
        o.setCountry("India");
        o.setPostalCode("600001");
        o.setTerritory("APAC");
        return o;
    }

    private Employee createEmployee() {
        Office office = createOffice();

        Employee emp = new Employee();
        emp.setEmployeeNumber(1);
        emp.setFirstName("Test");
        emp.setLastName("User");
        emp.setEmail("test@mail.com");
        emp.setExtension("x123");
        emp.setJobTitle("Developer");
        emp.setOffice(office); // 🔥 IMPORTANT

        return emp;
    }

    @Test
    void testCreateOffice() {
        OfficeRequestDTO dto = new OfficeRequestDTO();
        dto.setOfficeCode("1");
        dto.setCity("Chennai");
        dto.setPhone("123");
        dto.setAddressLine1("Street");
        dto.setCountry("India");
        dto.setPostalCode("600001");
        dto.setTerritory("APAC");

        when(officeRepository.save(any(Office.class))).thenReturn(createOffice());

        assertNotNull(officeService.createOffice(dto));
    }

    @Test
    void testGetAllOffices() {
        when(officeRepository.findAll()).thenReturn(List.of(createOffice()));

        assertEquals(1, officeService.getAllOffices().size());
    }

    @Test
    void testGetEmployeesByOffice() {
        when(officeRepository.findById("1")).thenReturn(Optional.of(createOffice()));
        when(employeeRepository.findByOfficeOfficeCode("1"))
                .thenReturn(List.of(createEmployee()));

        assertEquals(1, officeService.getEmployeesByOffice("1").size());
    }

    @Test
    void testOfficeNotFound() {
        when(officeRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> officeService.getOfficeByCode("1"));
    }

    @Test
    void testEmployeesNotFound() {
        when(officeRepository.findById("1")).thenReturn(Optional.of(createOffice()));
        when(employeeRepository.findByOfficeOfficeCode("1"))
                .thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> officeService.getEmployeesByOffice("1"));
    }
}