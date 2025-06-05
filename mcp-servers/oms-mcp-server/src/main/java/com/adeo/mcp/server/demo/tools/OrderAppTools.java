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
        List<OrderDto> orders = orderAppService.getOrders();
        return orders;
    }

    @Tool(description = "Retrieves a specific order by its ID.")
    OrderDto getOrder(@ToolParam(description = "The unique identifier of the order to retrieve") Long orderId) {
        OrderDto order = orderAppService.getOrder(orderId);
        return order;
    }

    @Tool(description = "Move forward the order status to the next step in the process. Should be used only if the order is in a valid state to be moved forward.")
    OrderDto moveForwardOrder(@ToolParam(description = "The unique identifier of the order to move forward") Long orderId) {
        OrderDto order = orderAppService.moveOrderForward(orderId);
        return order;
    }
}
