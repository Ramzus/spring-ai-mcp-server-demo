package com.adeo.mcp.server.demo;

import com.adeo.mcp.server.demo.tools.IncidentAppTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IncidentMcpServer {

    public static void main(String[] args) {
        SpringApplication.run(IncidentMcpServer.class, args);
    }

    @Bean
    public ToolCallbackProvider omsTools(IncidentAppTools incidentAppTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(incidentAppTools)
                .build();
    }

}
