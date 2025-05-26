package com.adeo.demo.payment.backend.web.dto;

import com.adeo.demo.payment.backend.web.dto.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private Long orderId;
    private String customerName;
    private LocalDate paymentDate;
    private double totalAmount;
    private PaymentStatus status;
}
