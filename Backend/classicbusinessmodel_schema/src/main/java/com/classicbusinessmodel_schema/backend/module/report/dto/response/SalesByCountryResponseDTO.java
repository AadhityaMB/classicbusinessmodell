package com.classicbusinessmodel_schema.backend.module.report.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class SalesByCountryResponseDTO {
    private String country;
    private BigDecimal totalSales;
}