package com.adeo.demo.order.backend.services;

import com.adeo.demo.order.backend.persistence.Order;
import com.adeo.demo.order.backend.persistence.OrderRepository;
import com.adeo.demo.order.backend.web.dto.OrderDto;
import com.adeo.demo.order.backend.web.dto.enums.OrderStatus;
import com.adeo.demo.order.backend.web.dto.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentServiceClient paymentServiceClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, PaymentServiceClient paymentServiceClient) {
        this.orderRepository = orderRepository;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toNewEntity(orderDto);
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
    public OrderDto nextOrderStep(Long orderId) {
        Optional<Order> existing = orderRepository.findById(orderId);
        if (existing.isPresent()) {
            Order currentOrder = existing.get();
            currentOrder.setStatus(getNextOrderStatus(orderId));
            Order saved = orderRepository.save(currentOrder);
            return toDto(saved);
        }
        return null;
    }

    private OrderStatus getNextOrderStatus(Long orderId) {
        OrderDto orderDto = getOrder(orderId);
        OrderStatus currentStatus = orderDto.getStatus();
        PaymentStatus paymentStatus = getPaymentStatus(orderId);
        OrderStatus newStatus = switch (currentStatus) {
            case CREATED -> OrderStatus.PENDING;
            case PENDING -> OrderStatus.SHIPPED;
            case SHIPPED -> OrderStatus.DELIVERED;
            case DELIVERED -> OrderStatus.FINISHED;
            case CANCELLED, FINISHED -> throw new IllegalStateException("Cannot progress from CANCELLED status");
        };
        return (paymentStatus == PaymentStatus.COMPLETED) ? newStatus : OrderStatus.PENDING; // If payment is not completed, stay in PENDING
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Optional<Order> existingOrder = orderRepository.findById(orderId); // Ensure the order exists before deletion
        if (existingOrder.isEmpty() ){
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
        if(existingOrder.get().getStatus() != OrderStatus.FINISHED || existingOrder.get().getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot delete order that is not FINISHED or CANCELLED");
        }
        orderRepository.deleteById(orderId);
    }    // --- Mapping helpers ---
    private OrderDto toDto(Order order) {
        PaymentStatus paymentStatus = getPaymentStatus(order.getId());
        return new OrderDto(
                order.getId(),
                order.getCustomerName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getNumberOfItems(),
                order.getTotalAmount(),
                paymentStatus
        );
    }

    private PaymentStatus getPaymentStatus(Long orderId) {
        PaymentStatus paymentStatus = paymentServiceClient.getPaymentStatusByOrderId(orderId);
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.UNKNOWN; // Default to UNKNOWN if no payment status is found
        }
        return paymentStatus;
    }

    private Order toNewEntity(OrderDto dto) {
        Order order = toEntity(dto);
        order.setId(null); // Ensure a new entity is created
        order.setStatus(OrderStatus.CREATED);
        return order;
    }    private Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getOrderId());
        order.setCustomerName(dto.getCustomerName());
        order.setOrderDate(dto.getOrderDate());
        order.setTotalAmount(dto.getTotalAmount());
        order.setNumberOfItems(dto.getNumberOfItems());
        return order;
    }
}
