package com.classicbusinessmodel_schema.backend.module.report.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class HighRiskCustomerResponseDTO {
    private Integer customerNumber;
    private String customerName;
    private BigDecimal creditLimit;
    private BigDecimal totalOrderValue;
    private BigDecimal riskPercentage;
}