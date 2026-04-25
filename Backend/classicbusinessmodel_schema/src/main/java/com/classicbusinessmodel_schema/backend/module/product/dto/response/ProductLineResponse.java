package com.classicbusinessmodel_schema.backend.module.product.dto.response;

// Response DTO representing product line details
public class ProductLineResponse {

    private String productLine;
    private String textDescription;
    private String htmlDescription;

    public ProductLineResponse() {
    }

    public ProductLineResponse(String productLine, String textDescription, String htmlDescription) {
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