package com.adeo.mcp.server.demo.tools;

import com.adeo.mcp.server.demo.service.OrderAppService;
import com.adeo.mcp.server.demo.service.dto.OrderDto;
import org.springframework.ai.tool.annotation.Tool;
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
        System.out.println("Tool invoked: getOrders - Retrieving customer orders");
        List<OrderDto> orders = orderAppService.getOrders();
        System.out.println("Returning " + orders.size() + " customer orders from tool");
        return orders;
    }

}
