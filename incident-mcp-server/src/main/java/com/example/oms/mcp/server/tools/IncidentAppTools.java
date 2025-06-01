package com.example.oms.mcp.server.tools;

import com.example.oms.mcp.server.service.IncidentAppService;
import com.example.oms.mcp.server.service.dto.IncidentDto;
import com.example.oms.mcp.server.service.dto.enums.IncidentSeverity;
import com.example.oms.mcp.server.service.dto.enums.IncidentStatus;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncidentAppTools {    private final IncidentAppService incidentAppService;

    public IncidentAppTools(IncidentAppService incidentAppService) {
        this.incidentAppService = incidentAppService;
    }

    @Tool(description = "Returns a list of all incidents in the system.")
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
    }    @Tool(description = "Returns incidents filtered by their severity level.")
    List<IncidentDto> getIncidentsBySeverity(
            @ToolParam(description = "The severity level to filter by (LOW, MEDIUM, HIGH, CRITICAL)") IncidentSeverity severity) {
        List<IncidentDto> incidents = incidentAppService.getIncidentsBySeverity(severity);
        return incidents;
    }

    @Tool(description = "Creates a new incident with the given details.")
    IncidentDto createIncident(
            @ToolParam(description = "The title of the incident") String title,
            @ToolParam(description = "The description of the incident") String description,
            @ToolParam(description = "The severity level (LOW, MEDIUM, HIGH, CRITICAL)") IncidentSeverity severity,
            @ToolParam(description = "The initial status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)") IncidentStatus status,
            @ToolParam(description = "The name of the person reporting the incident") String reporterName,
            @ToolParam(description = "The person assigned to handle the incident (optional)") String assignedTo,
            @ToolParam(description = "The resolution description (optional)") String resolution,
            @ToolParam(description = "The creation date (format: yyyy-MM-dd), defaults to today if not provided") LocalDate createdDate,
            @ToolParam(description = "The last updated date (format: yyyy-MM-dd), defaults to today if not provided") LocalDate updatedDate) {

        // Use today's date if not provided
        LocalDate actualCreatedDate = createdDate != null ? createdDate : LocalDate.now();
        LocalDate actualUpdatedDate = updatedDate != null ? updatedDate : LocalDate.now();

        // Create a new incident DTO with the provided values
        IncidentDto newIncident = new IncidentDto(null, title, description, severity, status, 
                reporterName, assignedTo, resolution, actualCreatedDate, actualUpdatedDate);

        return incidentAppService.createIncident(newIncident);
    }

    @Tool(description = "Updates an existing incident by its ID.")
    IncidentDto updateIncident(
            @ToolParam(description = "The unique identifier of the incident to update") Long incidentId,
            @ToolParam(description = "The title of the incident") String title,
            @ToolParam(description = "The description of the incident") String description,
            @ToolParam(description = "The severity level (LOW, MEDIUM, HIGH, CRITICAL)") IncidentSeverity severity,
            @ToolParam(description = "The status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)") IncidentStatus status,
            @ToolParam(description = "The name of the person reporting the incident") String reporterName,
            @ToolParam(description = "The person assigned to handle the incident") String assignedTo,
            @ToolParam(description = "The resolution description") String resolution,
            @ToolParam(description = "The creation date (format: yyyy-MM-dd)") LocalDate createdDate,
            @ToolParam(description = "The last updated date (format: yyyy-MM-dd)") LocalDate updatedDate) {

        // Create updated incident DTO with the provided values
        IncidentDto updatedIncident = new IncidentDto(incidentId, title, description, severity, status,
                reporterName, assignedTo, resolution, createdDate, updatedDate);

        return incidentAppService.updateIncident(incidentId, updatedIncident);
    }
}
