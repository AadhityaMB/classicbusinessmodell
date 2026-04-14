package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "orderdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    private Integer quantityOrdered;
    private Double priceEach;
    private Integer orderLineNumber;

    //Many order details belong to one order
    @ManyToOne
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    private Orders order;

    //Many order details belong to one product
    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "productCode")
    private Product product;
}