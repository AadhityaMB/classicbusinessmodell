package com.classicbusinessmodel_schema.backend.module.customer.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    @NotNull(message = "Customer number is required")
    private Integer customerNumber;

    @NotBlank(message = "Customer name is required")
    @Size(max = 50)
    private String customerName;

    @NotBlank(message = "Contact last name is required")
    @Size(max = 50)
    private String contactLastName;

    @NotBlank(message = "Contact first name is required")
    @Size(max = 50)
    private String contactFirstName;

    @NotBlank(message = "Phone is required")
    @Size(max = 50)
    private String phone;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 50)
    private String addressLine1;

    private String addressLine2; // optional

    @NotBlank(message = "City is required")
    @Size(max = 50)
    private String city;

    private String state; // optional

    private String postalCode; // optional

    @NotBlank(message = "Country is required")
    @Size(max = 50)
    private String country;

    @DecimalMin(value = "0.0", message = "Credit limit cannot be negative")
    private BigDecimal creditLimit; // optional

    // Only ID of sales rep (not full object)
    private Integer salesRepEmployeeNumber; // optional
}