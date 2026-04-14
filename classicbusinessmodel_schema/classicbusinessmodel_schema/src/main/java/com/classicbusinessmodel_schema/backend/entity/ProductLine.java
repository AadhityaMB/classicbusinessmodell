package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "productlines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLine {

    @Id
    private String productLine;

    private String textDescription;

    private String htmlDescription;

    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "productLine")
    private List<Product> products;
}

