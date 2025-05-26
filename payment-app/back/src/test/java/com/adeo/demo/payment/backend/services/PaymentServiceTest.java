package com.adeo.demo.payment.backend.services;

import com.adeo.demo.payment.backend.persistence.Payment;
import com.adeo.demo.payment.backend.persistence.PaymentRepository;
import com.adeo.demo.payment.backend.web.dto.PaymentDto;
import com.adeo.demo.payment.backend.web.dto.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(paymentRepository);
        // Set a fixed value for random seed to make tests predictable
        ReflectionTestUtils.setField(paymentService, "random", new MockPredictableRandom());
    }

    @Test
    void getAllPayments() {
        // Arrange
        Payment payment1 = createPayment(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED);
        Payment payment2 = createPayment(2L, 102L, "Jane Smith", LocalDate.now(), 200.0, PaymentStatus.PENDING);
        
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        // Act
        List<PaymentDto> result = paymentService.getAllPayments();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPaymentId()).isEqualTo(1L);
        assertThat(result.get(0).getCustomerName()).isEqualTo("John Doe");
        assertThat(result.get(1).getPaymentId()).isEqualTo(2L);
        assertThat(result.get(1).getCustomerName()).isEqualTo("Jane Smith");
    }

    @Test
    void getPayment() {
        // Arrange
        Payment payment = createPayment(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        // Act
        PaymentDto result = paymentService.getPayment(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(1L);
        assertThat(result.getCustomerName()).isEqualTo("John Doe");
        assertThat(result.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
    }

    @Test
    void getPaymentsByOrderId() {
        // Arrange
        Long orderId = 101L;
        Payment payment1 = createPayment(1L, orderId, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED);
        Payment payment2 = createPayment(2L, orderId, "John Doe", LocalDate.now().minusDays(1), 50.0, PaymentStatus.COMPLETED);
        
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Arrays.asList(payment1, payment2));

        // Act
        List<PaymentDto> result = paymentService.getPaymentsByOrderId(orderId);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOrderId()).isEqualTo(orderId);
        assertThat(result.get(1).getOrderId()).isEqualTo(orderId);
    }

    @Test
    void createPayment() {
        // Arrange
        PaymentDto dto = new PaymentDto(null, 101L, "John Doe", null, 150.0, null);
        
        Payment savedPayment = createPayment(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.PENDING);
        when(paymentRepository.save(any())).thenReturn(savedPayment);

        // Act
        PaymentDto result = paymentService.createPayment(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(PaymentStatus.PENDING);
        
        // Verify default values are set
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        
        Payment capturedPayment = paymentCaptor.getValue();
        assertThat(capturedPayment.getStatus()).isEqualTo(PaymentStatus.PENDING);
        assertThat(capturedPayment.getPaymentDate()).isNotNull();
    }

    @Test
    void updatePayment() {
        // Arrange
        Long id = 1L;
        PaymentDto dto = new PaymentDto(id, 101L, "John Updated", LocalDate.now(), 200.0, PaymentStatus.COMPLETED);
        
        when(paymentRepository.findById(id)).thenReturn(Optional.of(
            createPayment(id, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.PENDING)
        ));
        
        Payment updatedPayment = createPayment(id, 101L, "John Updated", LocalDate.now(), 200.0, PaymentStatus.COMPLETED);
        when(paymentRepository.save(any())).thenReturn(updatedPayment);

        // Act
        PaymentDto result = paymentService.updatePayment(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(id);
        assertThat(result.getCustomerName()).isEqualTo("John Updated");
        assertThat(result.getTotalAmount()).isEqualTo(200.0);
        assertThat(result.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
    }

    @Test
    void retryPayment_success() {
        // Arrange
        Long id = 1L;
        Payment failedPayment = createPayment(id, 101L, "John Doe", LocalDate.now().minusDays(1), 150.0, PaymentStatus.FAILED);
        when(paymentRepository.findById(id)).thenReturn(Optional.of(failedPayment));
        
        Payment retriedPayment = createPayment(id, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED);
        when(paymentRepository.save(any())).thenReturn(retriedPayment);

        // Act
        PaymentDto result = paymentService.retryPayment(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
        
        // Verify payment date is updated
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        
        Payment capturedPayment = paymentCaptor.getValue();
        assertThat(capturedPayment.getPaymentDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void deletePayment() {
        // Act
        paymentService.deletePayment(1L);
        
        // Assert
        verify(paymentRepository).deleteById(1L);
    }

    // Helper method to create Payment entity
    private Payment createPayment(Long id, Long orderId, String customerName, LocalDate date, Double amount, PaymentStatus status) {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setOrderId(orderId);
        payment.setCustomerName(customerName);
        payment.setPaymentDate(date);
        payment.setTotalAmount(amount);
        payment.setStatus(status);
        return payment;
    }
    
    // Mock random for predictable test results
    private static class MockPredictableRandom extends java.util.Random {
        @Override
        public double nextDouble() {
            return 0.1; // This will ensure retry always succeeds (0.1 > 0.3 is false)
        }
    }
}
