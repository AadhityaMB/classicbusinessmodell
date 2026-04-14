package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
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

        private Date paymentDate;
        private Double amount;

        //Many payments belong to one customer
        @ManyToOne
        @MapsId("customerNumber")
        @JoinColumn(name = "customerNumber")
        private Customer customer;

}
