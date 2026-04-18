package com.classicbusinessmodel_schema.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailsId implements Serializable {

    private Integer order;
    private String product;

    public OrderDetailsId() {
    }


    public OrderDetailsId(Integer order, String product) {
        this.order = order;
        this.product = product;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailsId that = (OrderDetailsId) o;

        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product);
    }


    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}