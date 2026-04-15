package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "ordernumber")
    private Integer orderNumber;

    @Column(name = "orderdate", nullable = false)
    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @Column(name = "requireddate", nullable = false)
    @NotNull(message = "Required date is required")
    private LocalDate requiredDate;

    @Column(name = "shippeddate")
    private LocalDate shippedDate;

    @Column(name = "status", length = 15, nullable = false)
    @NotBlank(message = "Status is required")
    @Size(max = 15)
    private String status;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customernumber", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();
}