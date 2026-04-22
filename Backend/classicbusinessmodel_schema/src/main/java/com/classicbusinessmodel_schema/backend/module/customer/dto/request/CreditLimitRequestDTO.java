package com.classicbusinessmodel_schema.backend.module.customer.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;


// Why a separate DTO just for this?
//   We don't want the client to accidentally (or intentionally) change
//   other customer fields while updating the credit limit.


public class CreditLimitRequestDTO {

    @NotNull(message = "Credit limit is required")
    @DecimalMin(value = "0.0", message = "Credit limit cannot be negative")
    private BigDecimal creditLimit;

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public CreditLimitRequestDTO(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public CreditLimitRequestDTO() {
    }
}