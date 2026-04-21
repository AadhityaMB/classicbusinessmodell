package com.classicbusinessmodel_schema.backend.module.employee.service;

import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.OfficeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.OfficeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;

import java.util.List;

public interface OfficeService {

    OfficeResponseDTO createOffice(OfficeRequestDTO dto);

    List<OfficeResponseDTO> getAllOffices();

    OfficeResponseDTO getOfficeByCode(String officeCode);

    List<EmployeeResponseDTO> getEmployeesByOffice(String officeCode);
}