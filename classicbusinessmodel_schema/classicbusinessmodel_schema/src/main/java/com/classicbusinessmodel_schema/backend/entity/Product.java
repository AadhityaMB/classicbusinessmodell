package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

        @Id
        @Column(name = "productcode", length = 15)
        @NotBlank(message = "Product code is required")
        @Size(max = 15)
        private String productCode;

        @Column(name = "productname", length = 70, nullable = false)
        @NotBlank(message = "Product name is required")
        @Size(max = 70)
        private String productName;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "productline", nullable = false)
        @NotNull(message = "Product line is required")
        private ProductLine productLine;

        @Column(name = "productscale", length = 10, nullable = false)
        @NotBlank(message = "Product scale is required")
        @Size(max = 10)
        private String productScale;

        @Column(name = "productvendor", length = 50, nullable = false)
        @NotBlank(message = "Product vendor is required")
        @Size(max = 50)
        private String productVendor;

        @Column(name = "productdescription", columnDefinition = "TEXT", nullable = false)
        @NotBlank(message = "Product description is required")
        private String productDescription;

        @Column(name = "quantityinstock", nullable = false)
        @NotNull(message = "Quantity in stock is required")
        @Min(value = 0, message = "Stock cannot be negative")
        @Max(value = 32767, message = "Exceeds SMALLINT limit")
        private Integer quantityInStock;

        @Column(name = "buyprice", precision = 10, scale = 2, nullable = false)
        @NotNull(message = "Buy price is required")
        @DecimalMin(value = "0.01", message = "Buy price must be positive")
        private BigDecimal buyPrice;

        @Column(name = "msrp", precision = 10, scale = 2, nullable = false)
        @NotNull(message = "MSRP is required")
        @DecimalMin(value = "0.01", message = "MSRP must be positive")
        private BigDecimal msrp;

        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<OrderDetails> orderDetails = new ArrayList<>();
}