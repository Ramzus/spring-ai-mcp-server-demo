package com.adeo.mcp.server.demo.service;

import com.adeo.mcp.server.demo.service.dto.OrderDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderAppService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8081/orders";

    public List<OrderDto> getOrders() {
        OrderDto[] orders = restTemplate.getForObject(BASE_URL, OrderDto[].class);
        if (orders == null) {
            return List.of();
        }

        return List.of(orders);
    }

    public OrderDto getOrder(Long orderId) {
        String url = BASE_URL + "/" + orderId;

        try {
            return restTemplate.getForObject(url, OrderDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public OrderDto createOrder(OrderDto orderDto) {
        return restTemplate.postForObject(BASE_URL, orderDto, OrderDto.class);
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

    }

    public OrderDto moveOrderForward(Long orderId) {
        String url = BASE_URL + "/" + orderId + "/next";

        try {
            return restTemplate.postForObject(url, null, OrderDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
