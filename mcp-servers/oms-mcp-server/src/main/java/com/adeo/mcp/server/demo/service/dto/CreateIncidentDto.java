package com.adeo.mcp.server.demo.service.dto;

import com.adeo.mcp.server.demo.service.dto.enums.IncidentSeverity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for creating new incidents.
 */
public class CreateIncidentDto {
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("severity")
    private IncidentSeverity severity;
    
    @JsonProperty("reporterName")
    private String reporterName;
    
    @JsonProperty("assignedTo")
    private String assignedTo;
    
    @JsonProperty("resolution")
    private String resolution;

    // Default constructor
    public CreateIncidentDto() {}

    // Constructor with required fields
    public CreateIncidentDto(String title, String description, IncidentSeverity severity, String reporterName) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.reporterName = reporterName;
    }

    // Constructor with all fields
    public CreateIncidentDto(String title, String description, IncidentSeverity severity, 
                           String reporterName, String assignedTo, String resolution) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.reporterName = reporterName;
        this.assignedTo = assignedTo;
        this.resolution = resolution;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public IncidentSeverity getSeverity() { return severity; }
    public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    @Override
    public String toString() {
        return "CreateIncidentDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", severity=" + severity +
                ", reporterName='" + reporterName + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
