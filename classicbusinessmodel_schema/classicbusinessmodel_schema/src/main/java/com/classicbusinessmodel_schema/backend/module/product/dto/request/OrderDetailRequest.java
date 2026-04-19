package com.classicbusinessmodel_schema.backend.module.product.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public class OrderDetailRequest {

    @NotNull
    private Integer orderNumber;

    @NotBlank
    @Size(max = 15)
    private String productCode;

    @NotNull
    @Min(1)
    private Integer quantityOrdered;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal priceEach;

    @NotNull
    @Min(1)
    private Integer orderLineNumber;

    public OrderDetailRequest() {
    }

    public OrderDetailRequest(Integer orderNumber, String productCode, Integer quantityOrdered, BigDecimal priceEach, Integer orderLineNumber) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.orderLineNumber = orderLineNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(BigDecimal priceEach) {
        this.priceEach = priceEach;
    }

    public Integer getOrderLineNumber() {
        return orderLineNumber;
    }

    public void setOrderLineNumber(Integer orderLineNumber) {
        this.orderLineNumber = orderLineNumber;
    }
}