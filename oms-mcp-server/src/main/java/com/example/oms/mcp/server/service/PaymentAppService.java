package com.example.oms.mcp.server.service;

import com.example.oms.mcp.server.service.dto.PaymentDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PaymentAppService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8082/payments";

    public PaymentAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PaymentDto> getAllPayments() {
        ResponseEntity<List<PaymentDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<PaymentDto> payments = response.getBody();

        return payments;
    }

    public PaymentDto getPayment(Long paymentId) {
        String url = BASE_URL + "/" + paymentId;

        try {
            ResponseEntity<PaymentDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    PaymentDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public List<PaymentDto> getPaymentsByOrderId(Long orderId) {
        String url = BASE_URL + "/order/" + orderId;

        ResponseEntity<List<PaymentDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<PaymentDto> payments = response.getBody();

        return payments;
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

        ResponseEntity<PaymentDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                request,
                PaymentDto.class
        );
        return response.getBody();
    }

    public PaymentDto retryPayment(Long paymentId) {
        String url = BASE_URL + "/" + paymentId + "/retry";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<PaymentDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    PaymentDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public PaymentDto updatePayment(PaymentDto paymentDto) {
        String url = BASE_URL + "/" + paymentDto.getPaymentId();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

            ResponseEntity<PaymentDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, PaymentDto.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public boolean deletePayment(Long paymentId) {
        String url = BASE_URL + "/" + paymentId;

        try {
            restTemplate.delete(url);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
