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
    }

    @Tool(description = "Returns a list of customers orders.")
    List<OrderDto> getOrders() {
        System.out.println("Tool invoked: getOrders - Retrieving customer orders");
        List<OrderDto> orders = orderAppService.getOrders();
        System.out.println("Returning " + orders.size() + " customer orders from tool");
        return orders;
    }

    @Tool(description = "Retrieves a specific order by its ID.")
    OrderDto getOrder(@ToolParam(description = "The unique identifier of the order to retrieve") Long orderId) {
        System.out.println("Tool invoked: getOrder - Retrieving order with ID: " + orderId);
        OrderDto order = orderAppService.getOrder(orderId);
        if (order != null) {
            System.out.println("Successfully retrieved order for " + order.getCustomerName());
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
        return order;
    }

    @Tool(description = "Creates a new customer order with the given details.")
    OrderDto createOrder(
            @ToolParam(description = "The name of the customer placing the order") String customerName,
            @ToolParam(description = "The date when the order is placed (format: yyyy-MM-dd), defaults to today if not provided") LocalDate orderDate,
            @ToolParam(description = "The URL of a photo related to the order") String photoUrl,
            @ToolParam(description = "The total amount of the order") double totalAmount,
            @ToolParam(description = "The status of the order (CREATED, PENDING, SHIPPED, DELIVERED, CANCELLED)") OrderStatus status) {

        System.out.println("Tool invoked: createOrder - Creating new order for customer: " + customerName);

        // Use today's date if not provided
        LocalDate date = orderDate != null ? orderDate : LocalDate.now();

        // Create a new order DTO with the provided values
        OrderDto newOrder = new OrderDto(null, customerName, date, status, photoUrl, totalAmount);

        OrderDto createdOrder = orderAppService.createOrder(newOrder);
        System.out.println("Successfully created order with ID: " + createdOrder.getOrderId());

        return createdOrder;
    }

    @Tool(description = "Deletes an order by its ID.")
    void deleteOrder(@ToolParam(description = "The unique identifier of the order to delete") Long orderId) {
        System.out.println("Tool invoked: deleteOrder - Deleting order with ID: " + orderId);

        // Check if order exists before deleting
        OrderDto existingOrder = orderAppService.getOrder(orderId);
        if (existingOrder == null) {
            System.out.println("Cannot delete - order not found with ID: " + orderId);
            return;
        }

        orderAppService.deleteOrder(orderId);
        System.out.println("Successfully deleted order with ID: " + orderId);
    }

    @Tool(description = "Moves an order to the next status in the workflow (CREATED→PENDING→SHIPPED→DELIVERED→FINISHED).")
    OrderDto moveOrderForward(@ToolParam(description = "The unique identifier of the order to move forward") Long orderId) {
        System.out.println("Tool invoked: moveOrderForward - Moving order with ID: " + orderId);

        // Check if order exists before trying to move it forward
        OrderDto existingOrder = orderAppService.getOrder(orderId);
        if (existingOrder == null) {
            System.out.println("Cannot move order forward - order not found with ID: " + orderId);
            return null;
        }

        OrderDto updatedOrder = orderAppService.moveOrderForward(orderId);
        if (updatedOrder != null) {
            System.out.println("Successfully moved order with ID: " + orderId + " from status "
                    + existingOrder.getStatus() + " to " + updatedOrder.getStatus());
        } else {
            System.out.println("Failed to move order forward with ID: " + orderId);
        }

        return updatedOrder;
    }
}
