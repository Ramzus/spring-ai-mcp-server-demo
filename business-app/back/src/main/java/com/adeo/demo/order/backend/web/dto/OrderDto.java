package com.adeo.demo.order.backend.web.dto;

import com.adeo.demo.order.backend.web.dto.enums.OrderStatus;
import com.adeo.demo.order.backend.web.dto.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private String customerName;
    private LocalDate orderDate;
    private OrderStatus status;
    private Integer numberOfItems;
    private double totalAmount;
    private PaymentStatus paymentStatus;
}
