package com.classicbusinessmodel_schema.backend.module.report.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SalesByEmployeeResponseDTO {
    private Integer employeeNumber;
    private String employeeName;
    private BigDecimal totalSales;
}