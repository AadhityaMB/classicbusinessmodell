package com.classicbusinessmodel_schema.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "payments")
@IdClass(PaymentId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

        @Id
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customerNumber")
        private Customer customer;

        @Id
        @NotBlank
        @Size(max = 50)
        private String checkNumber;

        @NotNull
        private LocalDate paymentDate;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal amount;
}