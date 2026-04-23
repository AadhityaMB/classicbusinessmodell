package com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrderDetailsRequestDTO {

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotNull(message = "Quantity is required")
    private Integer quantityOrdered;

    @NotNull(message = "Price is required")
    private BigDecimal priceEach;

    @NotNull(message = "Order line number is required")
    private Integer orderLineNumber;

    public OrderDetailsRequestDTO() {}

    public OrderDetailsRequestDTO(String productCode, Integer quantityOrdered,
                                  BigDecimal priceEach, Integer orderLineNumber) {
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.orderLineNumber = orderLineNumber;
    }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public Integer getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }

    public BigDecimal getPriceEach() { return priceEach; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }

    public Integer getOrderLineNumber() { return orderLineNumber; }
    public void setOrderLineNumber(Integer orderLineNumber) { this.orderLineNumber = orderLineNumber; }
}