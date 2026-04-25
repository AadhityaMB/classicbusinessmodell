package com.classicbusinessmodel_schema.backend.module.employee.repository;

import com.classicbusinessmodel_schema.backend.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    @Query("SELECT o FROM Office o WHERE o.officeCode = :code")
    Optional<Office> findOfficeByCodeCustom(@Param("code") String code);

    @Query("SELECT o FROM Office o")
    List<Office> findAllOfficesCustom();

    @Transactional
    @Modifying
    @Query("UPDATE Office o SET o.city = :city WHERE o.officeCode = :code")
    int updateOfficeCity(@Param("code") String code,
                         @Param("city") String city);

    @Transactional
    @Modifying
    @Query("DELETE FROM Office o WHERE o.officeCode = :code")
    int deleteOfficeByCodeCustom(@Param("code") String code);
}