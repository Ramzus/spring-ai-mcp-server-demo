package com.adeo.mcp.server.demo.tools;

import com.adeo.mcp.server.demo.service.IncidentAppService;
import com.adeo.mcp.server.demo.service.dto.CreateIncidentDto;
import com.adeo.mcp.server.demo.service.dto.IncidentDto;
import com.adeo.mcp.server.demo.service.dto.enums.IncidentSeverity;
import com.adeo.mcp.server.demo.service.dto.enums.IncidentStatus;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentAppTools {

    private final IncidentAppService incidentAppService;

    public IncidentAppTools(IncidentAppService incidentAppService) {
        this.incidentAppService = incidentAppService;
    }    @Tool(description = "Returns a list of all incidents in the system.")
    List<IncidentDto> getAllIncidents() {
        List<IncidentDto> incidents = incidentAppService.getAllIncidents();
        return incidents;
    }

    @Tool(description = "Returns a list of current incidents (OPEN or IN_PROGRESS status).")
    List<IncidentDto> getCurrentIncidents() {
        List<IncidentDto> incidents = incidentAppService.getCurrentIncidents();
        return incidents;
    }

    @Tool(description = "Retrieves a specific incident by its ID.")
    IncidentDto getIncident(@ToolParam(description = "The unique identifier of the incident to retrieve") Long incidentId) {
        IncidentDto incident = incidentAppService.getIncident(incidentId);
        return incident;
    }

    @Tool(description = "Returns incidents filtered by their status.")
    List<IncidentDto> getIncidentsByStatus(
            @ToolParam(description = "The status to filter by (OPEN, IN_PROGRESS, RESOLVED, CLOSED)") IncidentStatus status) {
        List<IncidentDto> incidents = incidentAppService.getIncidentsByStatus(status);
        return incidents;
    }

    @Tool(description = "Returns incidents filtered by their severity level.")
    List<IncidentDto> getIncidentsBySeverity(
            @ToolParam(description = "The severity level to filter by (LOW, MEDIUM, HIGH, CRITICAL)") IncidentSeverity severity) {
        List<IncidentDto> incidents = incidentAppService.getIncidentsBySeverity(severity);
        return incidents;
    }
}
