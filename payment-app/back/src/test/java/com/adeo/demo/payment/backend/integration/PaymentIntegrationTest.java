package com.adeo.demo.payment.backend.integration;

import com.adeo.demo.payment.backend.web.dto.PaymentDto;
import com.adeo.demo.payment.backend.web.dto.enums.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PaymentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPayments() throws Exception {
        // Act & Assert - The data.sql should have inserted test data
        mockMvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8)) // We have 8 payments in data.sql
                .andExpect(jsonPath("$[0].paymentId").isNotEmpty())
                .andExpect(jsonPath("$[0].customerName").isNotEmpty());
    }

    @Test
    void createAndRetrievePayment() throws Exception {
        // Arrange - Create a new payment
        PaymentDto newPayment = new PaymentDto(null, 2000L, "Test User", LocalDate.now(), 300.0, PaymentStatus.PENDING);
        String newPaymentJson = objectMapper.writeValueAsString(newPayment);

        // Act 1 - Create the payment
        String resultJson = mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPaymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").isNotEmpty())
                .andExpect(jsonPath("$.customerName").value("Test User"))
                .andReturn().getResponse().getContentAsString();
        
        // Extract the created payment ID
        PaymentDto createdPayment = objectMapper.readValue(resultJson, PaymentDto.class);
        Long createdId = createdPayment.getPaymentId();
        
        // Act 2 - Retrieve the created payment
        mockMvc.perform(get("/payments/" + createdId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(createdId))
                .andExpect(jsonPath("$.customerName").value("Test User"));
    }
    
    @Test
    void updatePayment() throws Exception {
        // Arrange - Get the first payment to update
        String allPaymentsJson = mockMvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        PaymentDto[] payments = objectMapper.readValue(allPaymentsJson, PaymentDto[].class);
        PaymentDto firstPayment = payments[0];
        
        // Modify the payment
        firstPayment.setCustomerName("Updated Name");
        
        // Act - Update the payment
        mockMvc.perform(put("/payments/" + firstPayment.getPaymentId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstPayment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Updated Name"));
                
        // Verify the update
        mockMvc.perform(get("/payments/" + firstPayment.getPaymentId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Updated Name"));
    }
    
    @Test
    void retryFailedPayment() throws Exception {
        // Find a failed payment
        String allPaymentsJson = mockMvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        PaymentDto[] payments = objectMapper.readValue(allPaymentsJson, PaymentDto[].class);
        
        // Find the first failed payment
        PaymentDto failedPayment = null;
        for (PaymentDto payment : payments) {
            if (payment.getStatus() == PaymentStatus.FAILED) {
                failedPayment = payment;
                break;
            }
        }
        
        if (failedPayment != null) {
            // Retry the failed payment
            mockMvc.perform(post("/payments/" + failedPayment.getPaymentId() + "/retry")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
                    // Note: We don't assert on the status as it's randomly determined in the service
        }
    }
}
