package com.classicbusinessmodel_schema.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "orderNumber")
    private Integer orderNumber;

    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private String status;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerNumber", nullable = false)
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    public Orders() {
    }

    public Orders(Integer orderNumber, LocalDate orderDate, LocalDate requiredDate,
                  LocalDate shippedDate, String status, String comments,
                  Customer customer, List<OrderDetails> orderDetails) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDate requiredDate) {
        this.requiredDate = requiredDate;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetails> getOrderDetails() {

        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {

        this.orderDetails = orderDetails;
    }
}