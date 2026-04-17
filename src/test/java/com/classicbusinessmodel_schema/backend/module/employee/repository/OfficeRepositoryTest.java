package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OfficeRepositoryTest {

    @Autowired
    private OfficeRepository repository;

    @Autowired
    private TestEntityManager entityManager;

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

    // 1️⃣ Find by City
    @Test
    void testFindByCityCustom() {
        Office office = createOffice("1");
        entityManager.persist(office);

        List<Office> result = repository.findByCityCustom("Chennai");

        assertEquals(1, result.size());
    }

    // 2️⃣ Find by Country
    @Test
    void testFindByCountryCustom() {
        Office office = createOffice("2");
        entityManager.persist(office);

        List<Office> result = repository.findByCountryCustom("India");

        assertEquals(1, result.size());
    }

    // 3️⃣ Get All Cities
    @Test
    void testFindAllCities() {
        entityManager.persist(createOffice("3"));

        List<String> cities = repository.findAllCities();

        assertTrue(cities.contains("Chennai"));
    }
}