package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailsId implements Serializable {

    private Integer order;
    private String product;
}