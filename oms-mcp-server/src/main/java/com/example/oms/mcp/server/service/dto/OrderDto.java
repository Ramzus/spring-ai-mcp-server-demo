package com.example.oms.mcp.server.service.dto;

import com.example.oms.mcp.server.service.dto.enums.OrderStatus;
import com.example.oms.mcp.server.service.dto.enums.PaymentStatus;

import java.time.LocalDate;

public class OrderDto {

    private Long orderId;
    private String customerName;
    private LocalDate orderDate;
    private OrderStatus status;
    private Integer numberOfItems;
    private double totalAmount;
    private PaymentStatus paymentStatus;

    public OrderDto() {
        // Default constructor
    }

    public OrderDto(Long orderId, String customerName, LocalDate orderDate, OrderStatus status, String photoUrl, Integer numberOfItems, double totalAmount, PaymentStatus paymentStatus) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.status = status;
        this.numberOfItems = numberOfItems;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
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

    public OrderDto setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public OrderDto setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
        return this;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public OrderDto setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }
}