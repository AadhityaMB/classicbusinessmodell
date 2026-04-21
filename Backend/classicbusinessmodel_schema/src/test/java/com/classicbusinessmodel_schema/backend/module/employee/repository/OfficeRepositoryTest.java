package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OfficeRepositoryTest {

    @Autowired
    private OfficeRepository repository;

    private Office createOffice(String code) {
        Office o = new Office();
        o.setOfficeCode(code);
        o.setCity("Chennai");
        o.setPhone("123");
        o.setAddressLine1("Street");
        o.setCountry("India");
        o.setPostalCode("600001");
        o.setTerritory("APAC");
        return repository.save(o);
    }

    @Test
    void testFindOfficeByCodeCustom() {
        createOffice("1");
        Optional<Office> office = repository.findOfficeByCodeCustom("1");
        assertTrue(office.isPresent());
    }

    @Test
    void testFindAllOfficesCustom() {
        createOffice("2");
        assertFalse(repository.findAllOfficesCustom().isEmpty());
    }

    @Test
    void testUpdateOfficeCity() {
        createOffice("3");
        int updated = repository.updateOfficeCity("3", "Delhi");
        assertEquals(1, updated);
    }

    @Test
    void testDeleteOfficeByCodeCustom() {
        createOffice("4");
        int deleted = repository.deleteOfficeByCodeCustom("4");
        assertEquals(1, deleted);
    }

    @Test
    void testFindOfficeByCodeCustomNegative() {
        assertTrue(repository.findOfficeByCodeCustom("999").isEmpty());
    }
}