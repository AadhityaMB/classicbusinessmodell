package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, String> {

    // ✅ CUSTOM QUERY 1 - By City
    @Query("SELECT o FROM Office o WHERE o.city = :city")
    List<Office> findByCityCustom(String city);

    // ✅ CUSTOM QUERY 2 - By Country
    @Query("SELECT o FROM Office o WHERE o.country = :country")
    List<Office> findByCountryCustom(String country);

    // ✅ CUSTOM QUERY 3 - Get Cities only
    @Query("SELECT o.city FROM Office o")
    List<String> findAllCities();
}