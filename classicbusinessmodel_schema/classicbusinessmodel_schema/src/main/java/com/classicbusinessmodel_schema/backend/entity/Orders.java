package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    private Integer orderNumber;

    private Date orderDate;
    private Date requiredDate;
    private Date shippedDate;
    private String status;
    private String comments;

    //Many orders belong to one customer
    @ManyToOne
    @JoinColumn(name = "customerNumber")
    private Customer customer;

    //one order has many order details
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;
}