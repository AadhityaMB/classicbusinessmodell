package com.classicbusinessmodel_schema.backend.module.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private Integer    customerNumber;
    private String     customerName;
    private String     contactLastName;
    private String     contactFirstName;
    private String     phone;
    private String     addressLine1;
    private String     addressLine2;
    private String     city;
    private String     state;
    private String     postalCode;
    private String     country;
    private BigDecimal creditLimit;

    // Instead of nesting the whole Employee object, we send just two fields.
    // This avoids deep nesting in the JSON response.
    private Integer    salesRepEmployeeNumber;
    private String     salesRepName;
}