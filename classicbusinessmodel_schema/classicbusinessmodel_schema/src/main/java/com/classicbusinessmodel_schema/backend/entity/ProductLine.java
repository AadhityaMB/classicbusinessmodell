package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productlines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLine {

    @Id
    @Column(name = "productline", length = 50, nullable = false)
    @NotBlank(message = "Product line name is required")
    @Size(max = 50)
    private String productLine;

    @Column(name = "textdescription", length = 4000)
    @Size(max = 4000)
    private String textDescription;

    @Column(name = "htmldescription", columnDefinition = "TEXT")
    private String htmlDescription;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "productLine", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> products = new ArrayList<>();
}


