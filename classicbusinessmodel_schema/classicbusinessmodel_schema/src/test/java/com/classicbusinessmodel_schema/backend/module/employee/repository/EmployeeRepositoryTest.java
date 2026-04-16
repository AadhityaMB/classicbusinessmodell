package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Employee;
import com.classicbusinessmodel_schema.backend.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    // 🔧 Helper method
    private Office createOffice(String code) {
        Office office = new Office();
        office.setOfficeCode(code);
        office.setCity("Chennai");
        office.setPhone("1234567890");
        office.setAddressLine1("Street 1");
        office.setCountry("India");
        office.setPostalCode("600001");
        office.setTerritory("APAC");
        return office;
    }

    // 1️⃣ SAVE + FIND BY JOB TITLE
    @Test
    void testFindEmployeesByJobTitle() {
        Office office = createOffice("1");
        entityManager.persist(office);

        Employee emp = new Employee();
        emp.setEmployeeNumber(1);
        emp.setFirstName("Harini");
        emp.setLastName("C");
        emp.setEmail("h@test.com");
        emp.setExtension("x123");
        emp.setJobTitle("Developer");
        emp.setOffice(office);

        entityManager.persist(emp);

        List<Employee> result = repository.findEmployeesByJobTitle("Developer");

        assertEquals(1, result.size());
    }

    // 2️⃣ SUBORDINATES
    @Test
    void testFindSubordinatesCustom() {
        Office office = createOffice("2");
        entityManager.persist(office);

        Employee manager = new Employee();
        manager.setEmployeeNumber(10);
        manager.setFirstName("Manager");
        manager.setLastName("M");
        manager.setEmail("m@test.com");
        manager.setExtension("x111");
        manager.setJobTitle("Manager");
        manager.setOffice(office);

        entityManager.persist(manager);

        Employee emp = new Employee();
        emp.setEmployeeNumber(11);
        emp.setFirstName("Emp");
        emp.setLastName("E");
        emp.setEmail("e@test.com");
        emp.setExtension("x222");
        emp.setJobTitle("Dev");
        emp.setOffice(office);
        emp.setManager(manager);

        entityManager.persist(emp);

        List<Employee> result = repository.findSubordinatesCustom(10);

        assertEquals(1, result.size());
    }

    // 3️⃣ EMAIL LIST
    @Test
    void testFindAllEmployeeEmails() {
        Office office = createOffice("3");
        entityManager.persist(office);

        Employee emp = new Employee();
        emp.setEmployeeNumber(20);
        emp.setFirstName("Mail");
        emp.setLastName("Test");
        emp.setEmail("mail@test.com");
        emp.setExtension("x333");
        emp.setJobTitle("Tester");
        emp.setOffice(office);

        entityManager.persist(emp);

        List<String> emails = repository.findAllEmployeeEmails();

        assertTrue(emails.contains("mail@test.com"));
    }
}