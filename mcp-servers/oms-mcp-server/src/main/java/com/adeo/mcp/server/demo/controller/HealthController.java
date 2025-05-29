package com.adeo.mcp.server.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "OMS MCP Server");
        health.put("timestamp", LocalDateTime.now());
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "OMS MCP Server Demo");
        info.put("description", "Model Context Protocol Server for Order Management System");
        info.put("version", "1.0.0");
        info.put("scope", "Order, payment, and incident management");
        info.put("capabilities", new String[]{
            "Order CRUD operations",
            "Payment processing and management", 
            "Incident tracking and resolution"
        });
        info.put("endpoints", new String[]{
            "/health"
        });
        return ResponseEntity.ok(info);
    }
}
