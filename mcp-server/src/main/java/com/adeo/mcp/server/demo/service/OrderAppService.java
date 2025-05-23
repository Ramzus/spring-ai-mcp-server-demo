package com.adeo.mcp.server.demo.service;

import com.adeo.mcp.server.demo.service.dto.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderAppService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<OrderDto> getOrders() {
        System.out.println("Fetching orders from the order backend service");
        String url = "http://localhost:8081/orders";

        try {
            OrderDto[] orders = restTemplate.getForObject(url, OrderDto[].class);
            if (orders == null) {
                System.out.println("Received null response from order service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + orders.length + " orders from backend");
            return List.of(orders);
        } catch (Exception e) {
            System.out.println("Error fetching orders from backend service: " + e.getMessage());
            throw e;
        }
    }
}
