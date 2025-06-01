package com.example.oms.mcp.server.service;

import com.example.oms.mcp.server.service.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderAppService {

    private static final Logger logger = LoggerFactory.getLogger(OrderAppService.class);
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8081/orders";

    public OrderAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<OrderDto> getOrders() {
        logger.info("Fetching orders from the order backend service");

        try {
            OrderDto[] orders = restTemplate.getForObject(BASE_URL, OrderDto[].class);
            if (orders == null) {
                logger.warn("Received null response from order service");
                return List.of();
            }

            logger.info("Successfully retrieved {} orders from backend", orders.length);
            return List.of(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders from backend service: {}", e.getMessage());
            throw e;
        }
    }    public OrderDto getOrder(Long orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        String url = BASE_URL + "/" + orderId;

        try {
            return restTemplate.getForObject(url, OrderDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Order not found with ID: {}", orderId);
            return null;
        } catch (Exception e) {
            logger.error("Error fetching order with ID {}: {}", orderId, e.getMessage());
            throw e;
        }
    }    public OrderDto createOrder(OrderDto orderDto) {
        logger.info("Creating new order: {}", orderDto);

        try {
            return restTemplate.postForObject(BASE_URL, orderDto, OrderDto.class);
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage());
            throw e;
        }
    }    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        logger.info("Updating order with ID: {}", orderId);
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
            logger.warn("Order not found with ID: {}", orderId);
            return null;
        } catch (Exception e) {
            logger.error("Error updating order with ID {}: {}", orderId, e.getMessage());
            throw e;
        }
    }    public void deleteOrder(Long orderId) {
        logger.info("Deleting order with ID: {}", orderId);
        String url = BASE_URL + "/" + orderId;

        try {
            restTemplate.delete(url);
            logger.info("Successfully deleted order with ID: {}", orderId);
        } catch (Exception e) {
            logger.error("Error deleting order with ID {}: {}", orderId, e.getMessage());
            throw e;
        }
    }
      public OrderDto moveOrderForward(Long orderId) {
        logger.info("Moving order forward with ID: {}", orderId);
        String url = BASE_URL + "/" + orderId + "/next";
        
        try {
            return restTemplate.postForObject(url, null, OrderDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Order not found with ID: {}", orderId);
            return null;
        } catch (Exception e) {
            logger.error("Error moving order forward with ID {}: {}", orderId, e.getMessage());
            throw e;
        }
    }
}
