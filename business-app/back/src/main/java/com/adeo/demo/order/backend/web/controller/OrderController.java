package com.adeo.demo.order.backend.web.controller;


import com.adeo.demo.order.backend.services.OrderService;
import com.adeo.demo.order.backend.web.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        logger.info("Request received to fetch all orders");
        List<OrderDto> orders = orderService.getAllOrders();
        logger.info("Returning {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        logger.info("Request received to fetch order with id: {}", id);
        OrderDto order = orderService.getOrder(id);
        if (order != null) {
            logger.info("Order found: {}", order);
            return ResponseEntity.ok(order);
        } else {
            logger.warn("Order with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        logger.info("Request received to create a new order: {}", orderDto);
        OrderDto created = orderService.createOrder(orderDto);
        logger.info("Order created successfully with id: {}", created.getOrderId());
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{id}/next")
    public ResponseEntity<OrderDto> nextOrderStep(@PathVariable Long id) {
        logger.info("Request received to move forward order: {}", id);
        OrderDto updatedOrder = orderService.nextOrderStep(id);
        logger.info("Order move forward successfully with id {} and new status {}", updatedOrder.getOrderId(), updatedOrder.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        logger.info("Request received to update order with id: {}", id);
        orderDto.setOrderId(id);
        OrderDto updated = orderService.updateOrder(orderDto);
        if (updated != null) {
            logger.info("Order with id {} updated successfully", id);
            return ResponseEntity.ok(updated);
        } else {
            logger.warn("Order with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        logger.info("Request received to delete order with id: {}", id);
        orderService.deleteOrder(id);
        logger.info("Order with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
