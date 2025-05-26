package com.adeo.demo.payment.backend.services;

import com.adeo.demo.payment.backend.persistence.Payment;
import com.adeo.demo.payment.backend.persistence.PaymentRepository;
import com.adeo.demo.payment.backend.web.dto.PaymentDto;
import com.adeo.demo.payment.backend.web.dto.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final Random random = new Random();

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public PaymentDto getPayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.map(this::toDto).orElse(null);
    }
    
    public List<PaymentDto> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = toEntity(paymentDto);
        // Set initial status for new payments
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PENDING);
        }
        // Set payment date to today if not provided
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }
        Payment saved = paymentRepository.save(payment);
        return toDto(saved);
    }
    
    @Transactional
    public PaymentDto updatePayment(PaymentDto paymentDto) {
        if (paymentDto.getPaymentId() == null) return null;
        
        Optional<Payment> existing = paymentRepository.findById(paymentDto.getPaymentId());
        if (existing.isPresent()) {
            Payment payment = toEntity(paymentDto);
            Payment saved = paymentRepository.save(payment);
            return toDto(saved);
        }
        return null;
    }
    
    @Transactional
    public PaymentDto retryPayment(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            
            // Only retry failed payments
            if (payment.getStatus() == PaymentStatus.FAILED) {
                // Simulate payment processing with 70% success rate
                boolean isSuccessful = random.nextDouble() > 0.3;
                payment.setStatus(isSuccessful ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
                payment.setPaymentDate(LocalDate.now()); // Update payment date
                Payment saved = paymentRepository.save(payment);
                return toDto(saved);
            }
            return toDto(payment);
        }
        return null;
    }
    
    @Transactional
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
    
    // --- Mapping helpers ---
    private PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                payment.getCustomerName(),
                payment.getPaymentDate(),
                payment.getTotalAmount(),
                payment.getStatus()
        );
    }
    
    private Payment toEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setId(dto.getPaymentId());
        payment.setOrderId(dto.getOrderId());
        payment.setCustomerName(dto.getCustomerName());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setTotalAmount(dto.getTotalAmount());
        payment.setStatus(dto.getStatus());
        return payment;
    }
}
