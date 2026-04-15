package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
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
        @NotNull
        private PaymentId id;

        @NotNull
        private Date paymentDate;

        @NotNull
        private Double amount;

        //Many payments belong to one customer
        @ManyToOne
        @MapsId("customerNumber")
        @JoinColumn(name = "customerNumber")
        @NotNull
        private Customer customer;

}
