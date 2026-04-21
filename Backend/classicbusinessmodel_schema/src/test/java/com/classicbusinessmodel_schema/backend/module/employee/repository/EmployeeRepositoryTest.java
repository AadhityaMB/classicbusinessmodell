package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private OfficeRepository officeRepository;

    private Office createOffice(String code) {
        Office o = new Office();
        o.setOfficeCode(code);
        o.setCity("Chennai");
        o.setPhone("123");
        o.setAddressLine1("Street");
        o.setCountry("India");
        o.setPostalCode("600001");
        o.setTerritory("APAC");
        return officeRepository.save(o);
    }

    private Employee createEmployee(int id, Office office) {
        Employee e = new Employee();
        e.setEmployeeNumber(id);
        e.setFirstName("Test");
        e.setLastName("User");
        e.setEmail("test"+id+"@mail.com");
        e.setExtension("x111");
        e.setJobTitle("Developer");
        e.setOffice(office);
        return repository.save(e);
    }

    @Test
    void testFindEmployeeByIdCustom() {
        Office o = createOffice("1");
        createEmployee(1, o);
        Optional<Employee> emp = repository.findEmployeeByIdCustom(1);
        assertTrue(emp.isPresent());
    }

    @Test
    void testFindAllEmployeesCustom() {
        Office o = createOffice("2");
        createEmployee(2, o);
        assertFalse(repository.findAllEmployeesCustom().isEmpty());
    }

    @Test
    void testUpdateEmployeeJobTitle() {
        Office o = createOffice("3");
        createEmployee(3, o);
        int updated = repository.updateEmployeeJobTitle(3, "Manager");
        assertEquals(1, updated);
    }

    @Test
    void testDeleteEmployeeByIdCustom() {
        Office o = createOffice("4");
        createEmployee(4, o);
        int deleted = repository.deleteEmployeeByIdCustom(4);
        assertEquals(1, deleted);
    }

    @Test
    void testFindEmployeeByIdCustomNegative() {
        assertTrue(repository.findEmployeeByIdCustom(999).isEmpty());
    }
}