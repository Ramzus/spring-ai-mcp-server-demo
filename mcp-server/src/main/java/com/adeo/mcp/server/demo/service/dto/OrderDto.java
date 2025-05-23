package com.adeo.mcp.server.demo.service.dto;

import com.adeo.mcp.server.demo.service.dto.enums.OrderStatus;

import java.time.LocalDate;

public record OrderDto(
        Long orderId,
        String customerName,
        LocalDate orderDate,
        OrderStatus status,
        String photoUrl,
        double totalAmount
) {}

