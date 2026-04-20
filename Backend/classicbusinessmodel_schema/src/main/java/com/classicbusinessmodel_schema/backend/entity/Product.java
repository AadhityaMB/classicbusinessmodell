package com.classicbusinessmodel_schema.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
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

        @Column(columnDefinition = "TEXT")
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

        public Product() {
        }

        public Product(String productCode, String productName, ProductLine productLine, String productScale, String productVendor, String productDescription, Integer quantityInStock, BigDecimal buyPrice, BigDecimal MSRP, List<OrderDetails> orderDetails) {
                this.productCode = productCode;
                this.productName = productName;
                this.productLine = productLine;
                this.productScale = productScale;
                this.productVendor = productVendor;
                this.productDescription = productDescription;
                this.quantityInStock = quantityInStock;
                this.buyPrice = buyPrice;
                this.MSRP = MSRP;
                this.orderDetails = orderDetails;
        }

        public String getProductCode() {
                return productCode;
        }

        public void setProductCode(String productCode) {
                this.productCode = productCode;
        }

        public String getProductName() {
                return productName;
        }

        public void setProductName(String productName) {
                this.productName = productName;
        }

        public ProductLine getProductLine() {
                return productLine;
        }

        public void setProductLine(ProductLine productLine) {
                this.productLine = productLine;
        }

        public String getProductScale() {
                return productScale;
        }

        public void setProductScale(String productScale) {
                this.productScale = productScale;
        }

        public String getProductVendor() {
                return productVendor;
        }

        public void setProductVendor(String productVendor) {
                this.productVendor = productVendor;
        }

        public String getProductDescription() {
                return productDescription;
        }

        public void setProductDescription(String productDescription) {
                this.productDescription = productDescription;
        }

        public Integer getQuantityInStock() {
                return quantityInStock;
        }

        public void setQuantityInStock(Integer quantityInStock) {
                this.quantityInStock = quantityInStock;
        }

        public BigDecimal getBuyPrice() {
                return buyPrice;
        }

        public void setBuyPrice(BigDecimal buyPrice) {
                this.buyPrice = buyPrice;
        }

        public BigDecimal getMSRP() {
                return MSRP;
        }

        public void setMSRP(BigDecimal MSRP) {
                this.MSRP = MSRP;
        }

        public List<OrderDetails> getOrderDetails() {
                return orderDetails;
        }

        public void setOrderDetails(List<OrderDetails> orderDetails) {
                this.orderDetails = orderDetails;
        }
}