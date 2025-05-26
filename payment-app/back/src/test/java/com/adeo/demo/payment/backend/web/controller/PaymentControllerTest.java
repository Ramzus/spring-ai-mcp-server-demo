package com.adeo.demo.payment.backend.web.controller;

import com.adeo.demo.payment.backend.services.PaymentService;
import com.adeo.demo.payment.backend.web.dto.PaymentDto;
import com.adeo.demo.payment.backend.web.dto.enums.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPayments() throws Exception {
        // Arrange
        List<PaymentDto> payments = Arrays.asList(
            new PaymentDto(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED),
            new PaymentDto(2L, 102L, "Jane Smith", LocalDate.now(), 200.0, PaymentStatus.PENDING)
        );
        
        when(paymentService.getAllPayments()).thenReturn(payments);

        // Act & Assert
        mockMvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].paymentId").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[1].paymentId").value(2))
                .andExpect(jsonPath("$[1].customerName").value("Jane Smith"));
    }

    @Test
    void getPaymentById() throws Exception {
        // Arrange
        PaymentDto payment = new PaymentDto(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED);
        when(paymentService.getPayment(1L)).thenReturn(payment);

        // Act & Assert
        mockMvc.perform(get("/payments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }
    
    @Test
    void getPaymentById_notFound() throws Exception {
        // Arrange
        when(paymentService.getPayment(anyLong())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/payments/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void getPaymentsByOrderId() throws Exception {
        // Arrange
        List<PaymentDto> payments = Arrays.asList(
            new PaymentDto(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED),
            new PaymentDto(3L, 101L, "John Doe", LocalDate.now().minusDays(1), 50.0, PaymentStatus.COMPLETED)
        );
        
        when(paymentService.getPaymentsByOrderId(101L)).thenReturn(payments);

        // Act & Assert
        mockMvc.perform(get("/payments/order/101")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].orderId").value(101))
                .andExpect(jsonPath("$[1].orderId").value(101));
    }

    @Test
    void createPayment() throws Exception {
        // Arrange
        PaymentDto requestDto = new PaymentDto(null, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.PENDING);
        PaymentDto responseDto = new PaymentDto(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.PENDING);
        
        when(paymentService.createPayment(any())).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }
    
    @Test
    void retryPayment() throws Exception {
        // Arrange
        PaymentDto responseDto = new PaymentDto(1L, 101L, "John Doe", LocalDate.now(), 150.0, PaymentStatus.COMPLETED);
        when(paymentService.retryPayment(1L)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/payments/1/retry")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
    
    @Test
    void updatePayment() throws Exception {
        // Arrange
        PaymentDto requestDto = new PaymentDto(1L, 101L, "John Updated", LocalDate.now(), 200.0, PaymentStatus.COMPLETED);
        PaymentDto responseDto = new PaymentDto(1L, 101L, "John Updated", LocalDate.now(), 200.0, PaymentStatus.COMPLETED);
        
        when(paymentService.updatePayment(any())).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(put("/payments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Updated"))
                .andExpect(jsonPath("$.totalAmount").value(200.0));
    }
    
    @Test
    void deletePayment() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/payments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
