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
    }

    @Tool(description = "Returns a list of all incidents in the system.")
    List<IncidentDto> getAllIncidents() {
        System.out.println("Tool invoked: getAllIncidents - Retrieving all incidents");
        List<IncidentDto> incidents = incidentAppService.getAllIncidents();
        System.out.println("Returning " + incidents.size() + " incidents from tool");
        return incidents;
    }

    @Tool(description = "Returns a list of current incidents (OPEN or IN_PROGRESS status).")
    List<IncidentDto> getCurrentIncidents() {
        System.out.println("Tool invoked: getCurrentIncidents - Retrieving current incidents");
        List<IncidentDto> incidents = incidentAppService.getCurrentIncidents();
        System.out.println("Returning " + incidents.size() + " current incidents from tool");
        return incidents;
    }

    @Tool(description = "Retrieves a specific incident by its ID.")
    IncidentDto getIncident(@ToolParam(description = "The unique identifier of the incident to retrieve") Long incidentId) {
        System.out.println("Tool invoked: getIncident - Retrieving incident with ID: " + incidentId);
        IncidentDto incident = incidentAppService.getIncident(incidentId);
        if (incident != null) {
            System.out.println("Successfully retrieved incident: " + incident.getTitle());
        } else {
            System.out.println("Incident not found with ID: " + incidentId);
        }
        return incident;
    }

    @Tool(description = "Returns incidents filtered by their status.")
    List<IncidentDto> getIncidentsByStatus(
            @ToolParam(description = "The status to filter by (OPEN, IN_PROGRESS, RESOLVED, CLOSED)") IncidentStatus status) {
        System.out.println("Tool invoked: getIncidentsByStatus - Retrieving incidents with status: " + status);
        List<IncidentDto> incidents = incidentAppService.getIncidentsByStatus(status);
        System.out.println("Returning " + incidents.size() + " incidents with status " + status);
        return incidents;
    }

    @Tool(description = "Returns incidents filtered by their severity level.")
    List<IncidentDto> getIncidentsBySeverity(
            @ToolParam(description = "The severity level to filter by (LOW, MEDIUM, HIGH, CRITICAL)") IncidentSeverity severity) {
        System.out.println("Tool invoked: getIncidentsBySeverity - Retrieving incidents with severity: " + severity);
        List<IncidentDto> incidents = incidentAppService.getIncidentsBySeverity(severity);
        System.out.println("Returning " + incidents.size() + " incidents with severity " + severity);
        return incidents;
    }
}
