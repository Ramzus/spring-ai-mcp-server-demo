package com.adeo.mcp.server.demo.service;

import com.adeo.mcp.server.demo.service.dto.PaymentDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentAppService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8082/payments";

    public List<PaymentDto> getAllPayments() {
        System.out.println("Fetching all payments from the payment backend service");

        try {
            PaymentDto[] payments = restTemplate.getForObject(BASE_URL, PaymentDto[].class);
            if (payments == null) {
                System.out.println("Received null response from payment service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + payments.length + " payments from backend");
            return Arrays.asList(payments);
        } catch (Exception e) {
            System.out.println("Error fetching payments from backend service: " + e.getMessage());
            throw e;
        }
    }

    public PaymentDto getPayment(Long paymentId) {
        System.out.println("Fetching payment with ID: " + paymentId);
        String url = BASE_URL + "/" + paymentId;

        try {
            return restTemplate.getForObject(url, PaymentDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Payment not found with ID: " + paymentId);
            return null;
        } catch (Exception e) {
            System.out.println("Error fetching payment with ID " + paymentId + ": " + e.getMessage());
            throw e;
        }
    }

    public List<PaymentDto> getPaymentsByOrderId(Long orderId) {
        System.out.println("Fetching payments for order ID: " + orderId);
        String url = BASE_URL + "/order/" + orderId;

        try {
            PaymentDto[] payments = restTemplate.getForObject(url, PaymentDto[].class);
            if (payments == null) {
                System.out.println("Received null response from payment service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + payments.length + " payments for order " + orderId);
            return Arrays.asList(payments);
        } catch (Exception e) {
            System.out.println("Error fetching payments for order " + orderId + ": " + e.getMessage());
            throw e;
        }
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        System.out.println("Creating new payment for order: " + paymentDto.getOrderId());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.postForEntity(BASE_URL, request, PaymentDto.class);
            PaymentDto createdPayment = response.getBody();

            if (createdPayment != null) {
                System.out.println("Successfully created payment with ID: " + createdPayment.getPaymentId());
            }

            return createdPayment;
        } catch (Exception e) {
            System.out.println("Error creating payment: " + e.getMessage());
            throw e;
        }
    }

    public PaymentDto retryPayment(Long paymentId) {
        System.out.println("Retrying payment with ID: " + paymentId);
        String url = BASE_URL + "/" + paymentId + "/retry";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<PaymentDto> response = restTemplate.postForEntity(url, request, PaymentDto.class);
            PaymentDto retriedPayment = response.getBody();

            if (retriedPayment != null) {
                System.out.println("Successfully retried payment with ID: " + paymentId + ", new status: " + retriedPayment.getStatus());
            }

            return retriedPayment;
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Payment not found with ID: " + paymentId);
            return null;
        } catch (Exception e) {
            System.out.println("Error retrying payment with ID " + paymentId + ": " + e.getMessage());
            throw e;
        }
    }

    public PaymentDto updatePayment(PaymentDto paymentDto) {
        System.out.println("Updating payment with ID: " + paymentDto.getPaymentId());
        String url = BASE_URL + "/" + paymentDto.getPaymentId();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, PaymentDto.class);
            PaymentDto updatedPayment = response.getBody();

            if (updatedPayment != null) {
                System.out.println("Successfully updated payment with ID: " + updatedPayment.getPaymentId());
            }

            return updatedPayment;
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Payment not found with ID: " + paymentDto.getPaymentId());
            return null;
        } catch (Exception e) {
            System.out.println("Error updating payment with ID " + paymentDto.getPaymentId() + ": " + e.getMessage());
            throw e;
        }
    }

    public boolean deletePayment(Long paymentId) {
        System.out.println("Deleting payment with ID: " + paymentId);
        String url = BASE_URL + "/" + paymentId;

        try {
            restTemplate.delete(url);
            System.out.println("Successfully deleted payment with ID: " + paymentId);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Payment not found with ID: " + paymentId);
            return false;
        } catch (Exception e) {
            System.out.println("Error deleting payment with ID " + paymentId + ": " + e.getMessage());
            throw e;
        }
    }
}
