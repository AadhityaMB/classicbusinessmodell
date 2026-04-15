package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
@IdClass(OrderDetailsId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderNumber")
    private Orders order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode")
    private Product product;

    @NotNull
    private Integer quantityOrdered;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal priceEach;

    @NotNull
    private Integer orderLineNumber;
}