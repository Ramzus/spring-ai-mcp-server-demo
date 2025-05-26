package com.adeo.demo.order.backend.services;

import com.adeo.demo.order.backend.web.dto.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceClient {

    private final RestTemplate restTemplate;
    private final String paymentServiceBaseUrl;    public PaymentServiceClient(RestTemplate restTemplate,
                                @Value("${payment.service.base-url:http://localhost:8082}") String paymentServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceBaseUrl = paymentServiceBaseUrl;
    }

    public PaymentStatus getPaymentStatusByOrderId(Long orderId) {
        try {
            String url = paymentServiceBaseUrl + "/payments/order/" + orderId;
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> payments = restTemplate.getForObject(url, List.class);
            
            if (payments != null && !payments.isEmpty()) {
                // Get the status from the first payment (assuming one payment per order)
                String status = (String) payments.get(0).get("status");
                return PaymentStatus.valueOf(status);
            }
        } catch (HttpClientErrorException.NotFound e) {
            // No payment found for this order, return PENDING as default
            return PaymentStatus.PENDING;
        } catch (Exception e) {
            // In case of any error, return PENDING as fallback
            return PaymentStatus.PENDING;
        }
        
        return PaymentStatus.PENDING;
    }
}
