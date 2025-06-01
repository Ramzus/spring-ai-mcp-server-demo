package com.example.oms.mcp.server.service;

import com.example.oms.mcp.server.service.dto.OrderDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderAppService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8081/orders";

    public OrderAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }    public List<OrderDto> getOrders() {
        ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        List<OrderDto> orders = response.getBody();
        if (orders == null) {
            return List.of();
        }

        return orders;
    }    public OrderDto getOrder(Long orderId) {
        String url = BASE_URL + "/" + orderId;

        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    OrderDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }    public OrderDto createOrder(OrderDto orderDto) {
        ResponseEntity<OrderDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(orderDto),
                OrderDto.class
        );
        return response.getBody();
    }

    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        String url = BASE_URL + "/" + orderId;
        orderDto.setOrderId(orderId);

        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(orderDto),
                    OrderDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public void deleteOrder(Long orderId) {
        String url = BASE_URL + "/" + orderId;

        restTemplate.delete(url);

    }    public OrderDto moveOrderForward(Long orderId) {
        String url = BASE_URL + "/" + orderId + "/next";

        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    null,
                    OrderDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
