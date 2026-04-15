package com.classicbusinessmodel_schema.backend.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailsId implements Serializable {

    private Integer order;      // matches Orders PK type
    private String product;     // matches Product PK type
}