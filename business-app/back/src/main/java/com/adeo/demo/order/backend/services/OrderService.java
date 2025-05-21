package com.adeo.demo.order.backend.services;

import com.adeo.demo.order.backend.persistence.Order;
import com.adeo.demo.order.backend.persistence.OrderRepository;
import com.adeo.demo.order.backend.web.dto.OrderDto;
import com.adeo.demo.order.backend.web.dto.enums.OrderStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toEntity(orderDto);
        Order saved = orderRepository.save(order);
        return toDto(saved);
    }

    public OrderDto getOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(this::toDto).orElse(null);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        if (orderDto.getOrderId() == null) return null;
        Optional<Order> existing = orderRepository.findById(orderDto.getOrderId());
        if (existing.isPresent()) {
            Order order = toEntity(orderDto);
            Order saved = orderRepository.save(order);
            return toDto(saved);
        }
        return null;
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    // --- Mapping helpers ---
    private OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCustomerName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getPhotoUrl(),
                order.getTotalAmount()
        );
    }

    private Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getOrderId());
        order.setCustomerName(dto.getCustomerName());
        order.setOrderDate(dto.getOrderDate());
        order.setTotalAmount(dto.getTotalAmount());
        order.setPhotoUrl(dto.getPhotoUrl());
        order.setStatus(dto.getStatus());
        return order;
    }
}
