package com.classicbusinessmodel_schema.backend.module.product.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// Request DTO for adding or updating items in an order
public class OrderDetailRequest {

    // Order identifier (set from path variable in controller)
    private Integer orderNumber;

    // Product identifier for the order item
    @NotBlank(message = "Product code cannot be empty")
    @Size(max = 15)
    private String productCode;

    // Quantity of product ordered (minimum 1)
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantityOrdered;

    // Price per unit for the product
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal priceEach;

    // Line number representing position of item in order
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