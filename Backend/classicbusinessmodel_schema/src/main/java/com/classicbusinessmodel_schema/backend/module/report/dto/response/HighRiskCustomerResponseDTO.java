package com.classicbusinessmodel_schema.backend.module.report.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Builder
public class HighRiskCustomerResponseDTO {

    private Integer customerNumber;
    private String customerName;
    private BigDecimal creditLimit;
    private BigDecimal totalOrderValue;
    private BigDecimal riskPercentage;

    // Optional: if you still want constructor
    public HighRiskCustomerResponseDTO(
            Integer customerNumber,
            String customerName,
            BigDecimal creditLimit,
            BigDecimal totalOrderValue,
            BigDecimal riskPercentage
    ) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.creditLimit = creditLimit;
        this.totalOrderValue = totalOrderValue;
        this.riskPercentage = riskPercentage;
    }

    public HighRiskCustomerResponseDTO() {
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(BigDecimal totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public BigDecimal getRiskPercentage() {
        return riskPercentage;
    }

    public void setRiskPercentage(BigDecimal riskPercentage) {
        this.riskPercentage = riskPercentage;
    }
}