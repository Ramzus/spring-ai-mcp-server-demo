package com.adeo.mcp.server.demo;

import com.adeo.mcp.server.demo.tools.IncidentAppTools;
import com.adeo.mcp.server.demo.tools.OrderAppTools;
import com.adeo.mcp.server.demo.tools.PaymentAppTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OmsMcpServer {

    public static void main(String[] args) {
        SpringApplication.run(OmsMcpServer.class, args);
    }

    @Bean
    public ToolCallbackProvider omsTools(OrderAppTools orderAppTools, PaymentAppTools paymentAppTools, IncidentAppTools incidentAppTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(orderAppTools, paymentAppTools, incidentAppTools)
                .build();
    }

}
