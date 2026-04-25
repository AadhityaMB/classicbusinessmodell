package com.classicbusinessmodel_schema.backend.module.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Request DTO for creating or updating a product line
public class ProductLineRequest {

    // Unique name of the product line (acts as identifier)
    @NotBlank(message = "Product line cannot be empty")
    @Size(max = 50, message = "Product line must not exceed 50 characters")
    private String productLine;

    // Plain text description of the product line
    @Size(max = 4000, message = "Text description too long")
    private String textDescription;

    // HTML formatted description for rich content display
    @Size(max = 4000, message = "HTML description too long")
    private String htmlDescription;

    public ProductLineRequest() {
    }

    public ProductLineRequest(String productLine, String textDescription, String htmlDescription) {
        this.productLine = productLine;
        this.textDescription = textDescription;
        this.htmlDescription = htmlDescription;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }
}