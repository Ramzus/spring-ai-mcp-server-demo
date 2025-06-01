package com.example.oms.mcp.server.tools;

import com.example.oms.mcp.server.service.PaymentAppService;
import com.example.oms.mcp.server.service.dto.PaymentDto;
import com.example.oms.mcp.server.service.dto.enums.PaymentStatus;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentAppTools {

    private final PaymentAppService paymentAppService;

    public PaymentAppTools(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }    @Tool(description = "Returns a list of all payments in the system.")
    List<PaymentDto> getAllPayments() {
        List<PaymentDto> payments = paymentAppService.getAllPayments();
        return payments;
    }    @Tool(description = "Retrieves a specific payment by its ID.")
    PaymentDto getPayment(@ToolParam(description = "The unique identifier of the payment to retrieve") Long paymentId) {
        PaymentDto payment = paymentAppService.getPayment(paymentId);
        return payment;
    }    @Tool(description = "Returns all payments associated with a specific order.")
    List<PaymentDto> getPaymentsByOrderId(@ToolParam(description = "The order ID to search payments for") Long orderId) {
        List<PaymentDto> payments = paymentAppService.getPaymentsByOrderId(orderId);
        return payments;
    }
}
