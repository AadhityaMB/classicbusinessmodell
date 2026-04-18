package com.classicbusinessmodel_schema.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
public class ProductLine {

    @Id
    @Column(name = "productLine")
    private String productLine;

    @Column(name = "textdescription", length = 4000)
    private String textDescription;

    @Lob
    @Column(name = "htmldescription")
    private String htmlDescription;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "productLine", fetch = FetchType.LAZY)
    private List<Product> products;
}


