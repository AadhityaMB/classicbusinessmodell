package com.classicbusinessmodel_schema.backend.module.orders.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class OrderRequestDTO {

    @NotNull(message = "Customer number is required")
    private Integer customerNumber;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotNull(message = "Required date is required")
    private LocalDate requiredDate;

    private LocalDate shippedDate;

    @NotBlank(message = "Status is required")
    @Size(max = 15, message = "Status must not exceed 15 characters")
    private String status;

    private String comments;

    public OrderRequestDTO() {}

    public OrderRequestDTO(Integer customerNumber, LocalDate orderDate,
                           LocalDate requiredDate, LocalDate shippedDate,
                           String status, String comments) {
        this.customerNumber = customerNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
    }

    public Integer getCustomerNumber() { return customerNumber; }
    public void setCustomerNumber(Integer customerNumber) { this.customerNumber = customerNumber; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public LocalDate getRequiredDate() { return requiredDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }

    public LocalDate getShippedDate() { return shippedDate; }
    public void setShippedDate(LocalDate shippedDate) { this.shippedDate = shippedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}