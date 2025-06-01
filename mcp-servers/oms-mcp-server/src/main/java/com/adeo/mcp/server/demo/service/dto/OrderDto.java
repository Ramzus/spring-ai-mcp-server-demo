package com.adeo.mcp.server.demo.service.dto;

import com.adeo.mcp.server.demo.service.dto.enums.OrderStatus;
import com.adeo.mcp.server.demo.service.dto.enums.PaymentStatus;

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

    public OrderDto(Long orderId, String customerName, LocalDate orderDate, OrderStatus status, Integer numberOfItems, double totalAmount, PaymentStatus paymentStatus) {
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

    public OrderDto setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderDto setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderDto setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderDto setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public OrderDto setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
        return this;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderDto setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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