package com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto;

import com.classicbusinessmodel_schema.backend.module.orders.dto.responseDto.OrderDetailsResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class OrderResponseDTO {

    private Integer orderNumber;
    private Integer customerNumber;
    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private String status;
    private String comments;

    private List<OrderDetailsResponseDTO> orderDetails;

    public OrderResponseDTO() {}

    public OrderResponseDTO(Integer orderNumber, Integer customerNumber,
                            LocalDate orderDate, LocalDate requiredDate,
                            LocalDate shippedDate, String status,
                            String comments,
                            List<OrderDetailsResponseDTO> orderDetails) {
        this.orderNumber = orderNumber;
        this.customerNumber = customerNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.orderDetails = orderDetails;
    }

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

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

    public List<OrderDetailsResponseDTO> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetailsResponseDTO> orderDetails) { this.orderDetails = orderDetails; }
}