package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "orderdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderDetails.OrderDetailId.class)
public class OrderDetails {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordernumber", nullable = false)
    private Orders order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
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
    private Short orderLineNumber;

    // ---------- Composite Key ----------
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class OrderDetailId implements Serializable {
        private Integer order;
        private String product;
    }
}
