package com.adeo.mcp.server.demo;

import com.adeo.mcp.server.demo.tools.RunTopicTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider ticketTools(RunTopicTools runTopicTools) {
        return  MethodToolCallbackProvider.builder().toolObjects(runTopicTools).build();
    }

}
