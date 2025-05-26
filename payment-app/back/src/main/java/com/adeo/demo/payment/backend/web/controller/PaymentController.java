package com.adeo.demo.payment.backend.web.controller;

import com.adeo.demo.payment.backend.services.PaymentService;
import com.adeo.demo.payment.backend.web.dto.PaymentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payments")
public class PaymentController {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        logger.info("Request received to fetch all payments");
        List<PaymentDto> payments = paymentService.getAllPayments();
        logger.info("Returning {} payments", payments.size());
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id) {
        logger.info("Request received to fetch payment with id: {}", id);
        PaymentDto payment = paymentService.getPayment(id);
        if (payment != null) {
            logger.info("Payment found: {}", payment);
            return ResponseEntity.ok(payment);
        } else {
            logger.warn("Payment with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByOrderId(@PathVariable Long orderId) {
        logger.info("Request received to fetch payments for order with id: {}", orderId);
        List<PaymentDto> payments = paymentService.getPaymentsByOrderId(orderId);
        logger.info("Returning {} payments for order {}", payments.size(), orderId);
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        logger.info("Request received to create a new payment: {}", paymentDto);
        PaymentDto created = paymentService.createPayment(paymentDto);
        logger.info("Payment created successfully with id: {}", created.getPaymentId());
        return ResponseEntity.ok(created);
    }
    
    @PostMapping("/{id}/retry")
    public ResponseEntity<PaymentDto> retryPayment(@PathVariable Long id) {
        logger.info("Request received to retry payment with id: {}", id);
        PaymentDto retried = paymentService.retryPayment(id);
        if (retried != null) {
            logger.info("Payment retry successful for id: {}, new status: {}", id, retried.getStatus());
            return ResponseEntity.ok(retried);
        } else {
            logger.warn("Payment with id {} not found for retry", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
        logger.info("Request received to update payment with id: {}", id);
        paymentDto.setPaymentId(id);
        PaymentDto updated = paymentService.updatePayment(paymentDto);
        if (updated != null) {
            logger.info("Payment with id {} updated successfully", id);
            return ResponseEntity.ok(updated);
        } else {
            logger.warn("Payment with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        logger.info("Request received to delete payment with id: {}", id);
        paymentService.deletePayment(id);
        logger.info("Payment with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
