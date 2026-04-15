package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

        @EmbeddedId
        private PaymentId id;

        @Column(name = "paymentdate", nullable = false)
        @NotNull(message="Payment date is required")
        private Date paymentDate;

        @NotNull(message = "Amount is required")
        @Column(name = "amount",precision=10, scale=2, nullable=false)
        @DecimalMin(value = "0.0", inclusive=false,message = "Amount must be positive")
        private Double amount;

        //Many payments belong to one customer
        @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("customerNumber")
        @JoinColumn(name = "customernumber" , nullable = false)
        private Customer customer;

}
