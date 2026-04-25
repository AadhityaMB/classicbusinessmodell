package com.classicbusinessmodel_schema.backend.module.product.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// Request DTO for creating a new product with validation rules
public class CreateProductRequest {

    // Unique identifier for the product
    @NotBlank(message = "Product code cannot be empty")
    @Size(max = 15)
    private String productCode;

    @NotBlank
    @Size(max = 70)
    private String productName;

    // Product category reference (must match existing product line)
    @NotBlank
    @Size(max = 50)
    private String productLine;

    @NotBlank
    @Size(max = 10)
    private String productScale;

    @NotBlank
    @Size(max = 50)
    private String productVendor;

    @NotBlank
    private String productDescription;

    // Available stock quantity (cannot be negative)
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer quantityInStock;

    // Available stock quantity (cannot be negative)
    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    private BigDecimal buyPrice;

    // Selling price (must be greater than zero)
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal msrp;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, Integer quantityInStock, BigDecimal buyPrice, BigDecimal msrp) {
        this.productCode = productCode;
        this.productName = productName;
        this.productLine = productLine;
        this.productScale = productScale;
        this.productVendor = productVendor;
        this.productDescription = productDescription;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.msrp = msrp;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductScale() {
        return productScale;
    }

    public void setProductScale(String productScale) {
        this.productScale = productScale;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }
}