package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {
}
