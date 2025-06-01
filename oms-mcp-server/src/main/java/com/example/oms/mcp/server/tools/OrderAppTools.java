package com.example.oms.mcp.server.tools;

import com.example.oms.mcp.server.service.OrderAppService;
import com.example.oms.mcp.server.service.dto.OrderDto;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAppTools {

    private final OrderAppService orderAppService;

    public OrderAppTools(OrderAppService orderAppService) {
        this.orderAppService = orderAppService;
    }

    @Tool(description = "Returns a list of customers orders.")
    List<OrderDto> getOrders() {
        List<OrderDto> orders = orderAppService.getOrders();
        return orders;
    }

    @Tool(description = "Retrieves a specific order by its ID.")
    OrderDto getOrder(@ToolParam(description = "The unique identifier of the order to retrieve") Long orderId) {
        OrderDto order = orderAppService.getOrder(orderId);
        return order;
    }

    @Tool(description = "Moves an order to the next status.")
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
