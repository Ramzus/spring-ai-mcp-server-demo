package com.adeo.mcp.server.demo.service.dto;

import com.adeo.mcp.server.demo.service.dto.enums.OrderStatus;

import java.time.LocalDate;

public class OrderDto {

    private Long orderId;  // Unique identifier for the order
    private String customerName;  // Name of the customer who placed the order
    private LocalDate orderDate;  // Date when the order was placed
    private OrderStatus status;  // Current status of the order (e.g., CREATED, PENDING, SHIPPED, DELIVERED, CANCELLED)
    private String photoUrl;  // URL of the product photo associated with the order
    private double totalAmount;  // Total amount for the order
    public OrderDto() {
        // Default constructor
    }
    public OrderDto(Long orderId, String customerName, LocalDate orderDate, OrderStatus status, String photoUrl, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.status = status;
        this.photoUrl = photoUrl;
        this.totalAmount = totalAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderDto setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderDto setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public OrderDto setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderDto setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderDto setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public OrderDto setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
}