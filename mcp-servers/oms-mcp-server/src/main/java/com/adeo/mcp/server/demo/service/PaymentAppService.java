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
    private final String BASE_URL = "http://localhost:8082/payments";    public List<PaymentDto> getAllPayments() {
        try {
            PaymentDto[] payments = restTemplate.getForObject(BASE_URL, PaymentDto[].class);
            if (payments == null) {
                return List.of();
            }

            return Arrays.asList(payments);
        } catch (Exception e) {
            throw e;
        }
    }    public PaymentDto getPayment(Long paymentId) {
        String url = BASE_URL + "/" + paymentId;

        try {
            return restTemplate.getForObject(url, PaymentDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }    public List<PaymentDto> getPaymentsByOrderId(Long orderId) {
        String url = BASE_URL + "/order/" + orderId;

        try {
            PaymentDto[] payments = restTemplate.getForObject(url, PaymentDto[].class);
            if (payments == null) {
                return List.of();
            }

            return Arrays.asList(payments);
        } catch (Exception e) {
            throw e;
        }
    }    public PaymentDto createPayment(PaymentDto paymentDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.postForEntity(BASE_URL, request, PaymentDto.class);
            PaymentDto createdPayment = response.getBody();

            return createdPayment;
        } catch (Exception e) {
            throw e;
        }
    }    public PaymentDto retryPayment(Long paymentId) {
        String url = BASE_URL + "/" + paymentId + "/retry";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<PaymentDto> response = restTemplate.postForEntity(url, request, PaymentDto.class);
            PaymentDto retriedPayment = response.getBody();

            return retriedPayment;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }    public PaymentDto updatePayment(PaymentDto paymentDto) {
        String url = BASE_URL + "/" + paymentDto.getPaymentId();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, PaymentDto.class);
            PaymentDto updatedPayment = response.getBody();

            return updatedPayment;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }    public boolean deletePayment(Long paymentId) {
        String url = BASE_URL + "/" + paymentId;

        try {
            restTemplate.delete(url);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw e;
        }
    }
}
