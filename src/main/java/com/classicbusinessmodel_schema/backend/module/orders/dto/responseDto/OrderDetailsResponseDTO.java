package com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto;

import java.math.BigDecimal;

public class OrderDetailsResponseDTO {

    private String productCode;
    private Integer quantityOrdered;
    private BigDecimal priceEach;
    private Integer orderLineNumber;

    public OrderDetailsResponseDTO() {}

    public OrderDetailsResponseDTO(String productCode, Integer quantityOrdered,
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