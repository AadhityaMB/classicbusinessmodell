package com.classicbusinessmodel_schema.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
public class Product {

        @Id
        @Column(name = "productCode")
        private String productCode;

        @NotBlank
        @Size(max = 70)
        private String productName;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "productLine", nullable = false)
        private ProductLine productLine;

        @NotBlank
        @Size(max = 10)
        private String productScale;

        @NotBlank
        @Size(max = 50)
        private String productVendor;

        @NotBlank
        private String productDescription;

        @NotNull
        private Integer quantityInStock;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal buyPrice;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal MSRP;

        @JsonIgnore
        @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
        private List<OrderDetails> orderDetails;
}