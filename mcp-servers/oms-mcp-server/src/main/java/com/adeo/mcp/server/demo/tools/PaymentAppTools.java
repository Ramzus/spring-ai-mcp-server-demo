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
    }

    @Tool(description = "Returns a list of all payments in the system.")
    List<PaymentDto> getAllPayments() {
        System.out.println("Tool invoked: getAllPayments - Retrieving all payments");
        List<PaymentDto> payments = paymentAppService.getAllPayments();
        System.out.println("Returning " + payments.size() + " payments from tool");
        return payments;
    }

    @Tool(description = "Retrieves a specific payment by its ID.")
    PaymentDto getPayment(@ToolParam(description = "The unique identifier of the payment to retrieve") Long paymentId) {
        System.out.println("Tool invoked: getPayment - Retrieving payment with ID: " + paymentId);
        PaymentDto payment = paymentAppService.getPayment(paymentId);
        if (payment != null) {
            System.out.println("Successfully retrieved payment for customer: " + payment.getCustomerName());
        } else {
            System.out.println("Payment not found with ID: " + paymentId);
        }
        return payment;
    }

    @Tool(description = "Returns all payments associated with a specific order.")
    List<PaymentDto> getPaymentsByOrderId(@ToolParam(description = "The order ID to search payments for") Long orderId) {
        System.out.println("Tool invoked: getPaymentsByOrderId - Retrieving payments for order: " + orderId);
        List<PaymentDto> payments = paymentAppService.getPaymentsByOrderId(orderId);
        System.out.println("Returning " + payments.size() + " payments for order " + orderId);
        return payments;
    }

    @Tool(description = "Creates a new payment with the given details.")
    PaymentDto createPayment(
            @ToolParam(description = "The order ID this payment is for") Long orderId,
            @ToolParam(description = "The name of the customer making the payment") String customerName,
            @ToolParam(description = "The payment date (format: yyyy-MM-dd), defaults to today if not provided") LocalDate paymentDate,
            @ToolParam(description = "The total amount of the payment") double totalAmount,
            @ToolParam(description = "The payment status (PENDING, COMPLETED, FAILED)") PaymentStatus status) {

        System.out.println("Tool invoked: createPayment - Creating new payment for order: " + orderId);
        
        // Set default payment date to today if not provided
        LocalDate actualPaymentDate = paymentDate != null ? paymentDate : LocalDate.now();
        
        PaymentDto paymentDto = new PaymentDto(null, orderId, customerName, actualPaymentDate, totalAmount, status);
        PaymentDto createdPayment = paymentAppService.createPayment(paymentDto);
        
        if (createdPayment != null) {
            System.out.println("Successfully created payment with ID: " + createdPayment.getPaymentId());
        }
        
        return createdPayment;
    }

    @Tool(description = "Creates a new pending payment with minimal details.")
    PaymentDto createPendingPayment(
            @ToolParam(description = "The order ID this payment is for") Long orderId,
            @ToolParam(description = "The name of the customer making the payment") String customerName,
            @ToolParam(description = "The total amount of the payment") double totalAmount) {

        System.out.println("Tool invoked: createPendingPayment - Creating new pending payment for order: " + orderId);
        
        PaymentDto paymentDto = new PaymentDto(null, orderId, customerName, LocalDate.now(), totalAmount, PaymentStatus.PENDING);
        PaymentDto createdPayment = paymentAppService.createPayment(paymentDto);
        
        if (createdPayment != null) {
            System.out.println("Successfully created pending payment with ID: " + createdPayment.getPaymentId());
        }
        
        return createdPayment;
    }

    @Tool(description = "Retries a failed payment by its ID.")
    PaymentDto retryPayment(@ToolParam(description = "The unique identifier of the payment to retry") Long paymentId) {
        System.out.println("Tool invoked: retryPayment - Retrying payment with ID: " + paymentId);
        PaymentDto retriedPayment = paymentAppService.retryPayment(paymentId);
        if (retriedPayment != null) {
            System.out.println("Successfully retried payment with ID: " + paymentId + ", new status: " + retriedPayment.getStatus());
        } else {
            System.out.println("Failed to retry payment with ID: " + paymentId);
        }
        return retriedPayment;
    }

    @Tool(description = "Updates an existing payment.")
    PaymentDto updatePayment(
            @ToolParam(description = "The unique identifier of the payment to update") Long paymentId,
            @ToolParam(description = "The order ID this payment is for") Long orderId,
            @ToolParam(description = "The name of the customer making the payment") String customerName,
            @ToolParam(description = "The payment date (format: yyyy-MM-dd)") LocalDate paymentDate,
            @ToolParam(description = "The total amount of the payment") double totalAmount,
            @ToolParam(description = "The payment status (PENDING, COMPLETED, FAILED)") PaymentStatus status) {

        System.out.println("Tool invoked: updatePayment - Updating payment with ID: " + paymentId);
        
        PaymentDto paymentDto = new PaymentDto(paymentId, orderId, customerName, paymentDate, totalAmount, status);
        PaymentDto updatedPayment = paymentAppService.updatePayment(paymentDto);
        
        if (updatedPayment != null) {
            System.out.println("Successfully updated payment with ID: " + paymentId);
        } else {
            System.out.println("Failed to update payment with ID: " + paymentId);
        }
        
        return updatedPayment;
    }

    @Tool(description = "Deletes a payment by its ID.")
    boolean deletePayment(@ToolParam(description = "The unique identifier of the payment to delete") Long paymentId) {
        System.out.println("Tool invoked: deletePayment - Deleting payment with ID: " + paymentId);
        boolean deleted = paymentAppService.deletePayment(paymentId);
        if (deleted) {
            System.out.println("Successfully deleted payment with ID: " + paymentId);
        } else {
            System.out.println("Failed to delete payment with ID: " + paymentId);
        }
        return deleted;
    }
}
