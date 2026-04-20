package com.classicbusinessmodel_schema.backend.module.report.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class MonthlyRevenueResponseDTO {
    private Integer year;
    private Integer month;
    private BigDecimal revenue;
}
