package com.adeo.demo.order.backend.web.dto;

import com.adeo.demo.order.backend.web.dto.enums.OrderStatus;
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
    private String photoUrl;
    private double totalAmount;
}
