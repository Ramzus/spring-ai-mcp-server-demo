package com.adeo.mcp.server.demo.tools;

import com.adeo.mcp.server.demo.service.PaymentAppService;
import com.adeo.mcp.server.demo.service.dto.PaymentDto;
import com.adeo.mcp.server.demo.service.dto.enums.PaymentStatus;

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

    @Tool(description = "Creates a new payment with the given details.")
    PaymentDto createPayment(
            @ToolParam(description = "The order ID this payment is for") Long orderId,
            @ToolParam(description = "The name of the customer making the payment") String customerName,
            @ToolParam(description = "The payment date (format: yyyy-MM-dd), defaults to today if not provided") LocalDate paymentDate,
            @ToolParam(description = "The total amount of the payment") double totalAmount,            @ToolParam(description = "The payment status (PENDING, COMPLETED, FAILED)") PaymentStatus status) {

        // Set default payment date to today if not provided
        LocalDate actualPaymentDate = paymentDate != null ? paymentDate : LocalDate.now();
        
        PaymentDto paymentDto = new PaymentDto(null, orderId, customerName, actualPaymentDate, totalAmount, status);
        PaymentDto createdPayment = paymentAppService.createPayment(paymentDto);
        
        return createdPayment;
    }

    @Tool(description = "Creates a new pending payment with minimal details.")
    PaymentDto createPendingPayment(
            @ToolParam(description = "The order ID this payment is for") Long orderId,
            @ToolParam(description = "The name of the customer making the payment") String customerName,            @ToolParam(description = "The total amount of the payment") double totalAmount) {

        PaymentDto paymentDto = new PaymentDto(null, orderId, customerName, LocalDate.now(), totalAmount, PaymentStatus.PENDING);
        PaymentDto createdPayment = paymentAppService.createPayment(paymentDto);
        
        return createdPayment;
    }    @Tool(description = "Retries a failed payment by its ID.")
    PaymentDto retryPayment(@ToolParam(description = "The unique identifier of the payment to retry") Long paymentId) {
        PaymentDto retriedPayment = paymentAppService.retryPayment(paymentId);
        return retriedPayment;
    }

}
