package com.adeo.mcp.server.demo.service.dto;

import com.adeo.mcp.server.demo.service.dto.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Data Transfer Object for Payment entity.
 */
public class PaymentDto {
    
    @JsonProperty("paymentId")
    private Long paymentId;
    
    @JsonProperty("orderId")
    private Long orderId;
    
    @JsonProperty("customerName")
    private String customerName;
    
    @JsonProperty("paymentDate")
    private LocalDate paymentDate;
    
    @JsonProperty("totalAmount")
    private double totalAmount;
    
    @JsonProperty("status")
    private PaymentStatus status;

    // Default constructor
    public PaymentDto() {}

    // Constructor with all fields
    public PaymentDto(Long paymentId, Long orderId, String customerName, 
                     LocalDate paymentDate, double totalAmount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", paymentDate=" + paymentDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
}
