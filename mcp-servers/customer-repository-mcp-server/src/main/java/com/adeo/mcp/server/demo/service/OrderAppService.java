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
        System.out.println("Fetching orders from the order backend service");

        try {
            OrderDto[] orders = restTemplate.getForObject(BASE_URL, OrderDto[].class);
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

    public OrderDto getOrder(Long orderId) {
        System.out.println("Fetching order with ID: " + orderId);
        String url = BASE_URL + "/" + orderId;

        try {
            return restTemplate.getForObject(url, OrderDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Order not found with ID: " + orderId);
            return null;
        } catch (Exception e) {
            System.out.println("Error fetching order with ID " + orderId + ": " + e.getMessage());
            throw e;
        }
    }

    public OrderDto createOrder(OrderDto orderDto) {
        System.out.println("Creating new order: " + orderDto);

        try {
            return restTemplate.postForObject(BASE_URL, orderDto, OrderDto.class);
        } catch (Exception e) {
            System.out.println("Error creating order: " + e.getMessage());
            throw e;
        }
    }

    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        System.out.println("Updating order with ID: " + orderId);
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
            System.out.println("Order not found with ID: " + orderId);
            return null;
        } catch (Exception e) {
            System.out.println("Error updating order with ID " + orderId + ": " + e.getMessage());
            throw e;
        }
    }    public void deleteOrder(Long orderId) {
        System.out.println("Deleting order with ID: " + orderId);
        String url = BASE_URL + "/" + orderId;

        try {
            restTemplate.delete(url);
            System.out.println("Successfully deleted order with ID: " + orderId);
        } catch (Exception e) {
            System.out.println("Error deleting order with ID " + orderId + ": " + e.getMessage());
            throw e;
        }
    }
    
    public OrderDto moveOrderForward(Long orderId) {
        System.out.println("Moving order forward with ID: " + orderId);
        String url = BASE_URL + "/" + orderId + "/next";
        
        try {
            return restTemplate.postForObject(url, null, OrderDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Order not found with ID: " + orderId);
            return null;
        } catch (Exception e) {
            System.out.println("Error moving order forward with ID " + orderId + ": " + e.getMessage());
            throw e;
        }
    }
}
