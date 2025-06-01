package com.adeo.mcp.server.demo.tools;

import com.adeo.mcp.server.demo.service.OrderAppService;
import com.adeo.mcp.server.demo.service.dto.OrderDto;
import com.adeo.mcp.server.demo.service.dto.enums.OrderStatus;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderAppTools {

    private final OrderAppService orderAppService;

    public OrderAppTools(OrderAppService orderAppService) {
        this.orderAppService = orderAppService;
    }    @Tool(description = "Returns a list of customers orders.")
    List<OrderDto> getOrders() {
        List<OrderDto> orders = orderAppService.getOrders();
        return orders;
    }    @Tool(description = "Retrieves a specific order by its ID.")
    OrderDto getOrder(@ToolParam(description = "The unique identifier of the order to retrieve") Long orderId) {
        OrderDto order = orderAppService.getOrder(orderId);
        return order;
    }

    @Tool(description = "Creates a new customer order with the given details.")
    OrderDto createOrder(
            @ToolParam(description = "The name of the customer placing the order") String customerName,
            @ToolParam(description = "The date when the order is placed (format: yyyy-MM-dd), defaults to today if not provided") LocalDate orderDate,
            @ToolParam(description = "The URL of a photo related to the order") String photoUrl,
            @ToolParam(description = "The total amount of the order") double totalAmount,            @ToolParam(description = "The status of the order (CREATED, PENDING, SHIPPED, DELIVERED, CANCELLED)") OrderStatus status) {

        // Use today's date if not provided
        LocalDate date = orderDate != null ? orderDate : LocalDate.now();

        // Create a new order DTO with the provided values
        OrderDto newOrder = new OrderDto(null, customerName, date, status, photoUrl, totalAmount);

        OrderDto createdOrder = orderAppService.createOrder(newOrder);

        return createdOrder;
    }    @Tool(description = "Deletes an order by its ID.")
    void deleteOrder(@ToolParam(description = "The unique identifier of the order to delete") Long orderId) {
        // Check if order exists before deleting
        OrderDto existingOrder = orderAppService.getOrder(orderId);
        if (existingOrder == null) {
            return;
        }

        orderAppService.deleteOrder(orderId);
    }    @Tool(description = "Moves an order to the next status in the workflow (CREATED→PENDING→SHIPPED→DELIVERED→FINISHED).")
    OrderDto moveOrderForward(@ToolParam(description = "The unique identifier of the order to move forward") Long orderId) {
        // Check if order exists before trying to move it forward
        OrderDto existingOrder = orderAppService.getOrder(orderId);
        if (existingOrder == null) {
            return null;
        }

        OrderDto updatedOrder = orderAppService.moveOrderForward(orderId);

        return updatedOrder;
    }
}
