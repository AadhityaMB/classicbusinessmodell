package com.classicbusinessmodel_schema.backend.module.customer.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


// Why a separate DTO just for this?
//   We don't want the client to accidentally (or intentionally) change
//   other customer fields while updating the credit limit.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditLimitRequestDTO {

    @NotNull(message = "Credit limit is required")
    @DecimalMin(value = "0.0", message = "Credit limit cannot be negative")
    private BigDecimal creditLimit;
}