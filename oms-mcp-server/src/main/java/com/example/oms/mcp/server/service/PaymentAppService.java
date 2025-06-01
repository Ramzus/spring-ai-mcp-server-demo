package com.example.oms.mcp.server.service;

import com.example.oms.mcp.server.service.dto.PaymentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PaymentAppService.class);
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8082/payments";

    public PaymentAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PaymentDto> getAllPayments() {
        logger.info("Fetching all payments from the payment backend service");

        try {
            PaymentDto[] payments = restTemplate.getForObject(BASE_URL, PaymentDto[].class);
            if (payments == null) {
                logger.warn("Received null response from payment service");
                return List.of();
            }

            logger.info("Successfully retrieved {} payments from backend", payments.length);
            return Arrays.asList(payments);
        } catch (Exception e) {
            logger.error("Error fetching payments from backend service: {}", e.getMessage());
            throw e;
        }
    }    public PaymentDto getPayment(Long paymentId) {
        logger.info("Fetching payment with ID: {}", paymentId);
        String url = BASE_URL + "/" + paymentId;

        try {
            return restTemplate.getForObject(url, PaymentDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Payment not found with ID: {}", paymentId);
            return null;
        } catch (Exception e) {
            logger.error("Error fetching payment with ID {}: {}", paymentId, e.getMessage());
            throw e;
        }
    }    public List<PaymentDto> getPaymentsByOrderId(Long orderId) {
        logger.info("Fetching payments for order ID: {}", orderId);
        String url = BASE_URL + "/order/" + orderId;

        try {
            PaymentDto[] payments = restTemplate.getForObject(url, PaymentDto[].class);
            if (payments == null) {
                logger.warn("Received null response from payment service");
                return List.of();
            }

            logger.info("Successfully retrieved {} payments for order {}", payments.length, orderId);
            return Arrays.asList(payments);
        } catch (Exception e) {
            logger.error("Error fetching payments for order {}: {}", orderId, e.getMessage());
            throw e;
        }
    }    public PaymentDto createPayment(PaymentDto paymentDto) {
        logger.info("Creating new payment for order: {}", paymentDto.getOrderId());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.postForEntity(BASE_URL, request, PaymentDto.class);
            PaymentDto createdPayment = response.getBody();

            if (createdPayment != null) {
                logger.info("Successfully created payment with ID: {}", createdPayment.getPaymentId());
            }

            return createdPayment;
        } catch (Exception e) {
            logger.error("Error creating payment: {}", e.getMessage());
            throw e;
        }
    }    public PaymentDto retryPayment(Long paymentId) {
        logger.info("Retrying payment with ID: {}", paymentId);
        String url = BASE_URL + "/" + paymentId + "/retry";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<PaymentDto> response = restTemplate.postForEntity(url, request, PaymentDto.class);
            PaymentDto retriedPayment = response.getBody();

            if (retriedPayment != null) {
                logger.info("Successfully retried payment with ID: {}, new status: {}", paymentId, retriedPayment.getStatus());
            }

            return retriedPayment;
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Payment not found with ID: {}", paymentId);
            return null;
        } catch (Exception e) {
            logger.error("Error retrying payment with ID {}: {}", paymentId, e.getMessage());
            throw e;
        }
    }    public PaymentDto updatePayment(PaymentDto paymentDto) {
        logger.info("Updating payment with ID: {}", paymentDto.getPaymentId());
        String url = BASE_URL + "/" + paymentDto.getPaymentId();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, PaymentDto.class);
            PaymentDto updatedPayment = response.getBody();

            if (updatedPayment != null) {
                logger.info("Successfully updated payment with ID: {}", updatedPayment.getPaymentId());
            }

            return updatedPayment;
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Payment not found with ID: {}", paymentDto.getPaymentId());
            return null;
        } catch (Exception e) {
            logger.error("Error updating payment with ID {}: {}", paymentDto.getPaymentId(), e.getMessage());
            throw e;
        }
    }    public boolean deletePayment(Long paymentId) {
        logger.info("Deleting payment with ID: {}", paymentId);
        String url = BASE_URL + "/" + paymentId;

        try {
            restTemplate.delete(url);
            logger.info("Successfully deleted payment with ID: {}", paymentId);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Payment not found with ID: {}", paymentId);
            return false;
        } catch (Exception e) {
            logger.error("Error deleting payment with ID {}: {}", paymentId, e.getMessage());
            throw e;
        }
    }
}
