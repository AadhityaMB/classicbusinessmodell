package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

        @Id
        private String productCode;

        private String productName;

        @ManyToOne
        @JoinColumn(name = "productLine")
        private ProductLine productLine;

        private String productScale;

        private String productVendor;

        @Column(length = 1000)
        private String productDescription;

        private Integer quantityInStock;

        private Double buyPrice;
        private Double MSRP;



}
