package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order")   // ✅ FIXED (matches ID field)
    @JoinColumn(name = "ordernumber", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product") // ✅ FIXED (matches ID field)
    @JoinColumn(name = "productcode", nullable = false)
    private Product product;

    @Column(name = "quantityordered", nullable = false)
    @NotNull(message = "Quantity ordered is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantityOrdered;

    @Column(name = "priceeach", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal priceEach;

    @Column(name = "orderlinenumber", nullable = false)
    @NotNull(message = "Order line number is required")
    private short orderLineNumber;
}