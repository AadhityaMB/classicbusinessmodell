package com.classicbusinessmodel_schema.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "productlines")
public class ProductLine {

    @Id
    @Column(name = "productLine")
    private String productLine;

    @Column(columnDefinition = "TEXT")
    private String textDescription;

    @Column(columnDefinition = "TEXT")
    private String htmlDescription;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "productLine", fetch = FetchType.LAZY)
    private List<Product> products;

    public ProductLine() {
    }

    public ProductLine(String productLine, String textDescription, String htmlDescription, byte[] image,
            List<Product> products) {
        this.productLine = productLine;
        this.textDescription = textDescription;
        this.htmlDescription = htmlDescription;
        this.image = image;
        this.products = products;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}