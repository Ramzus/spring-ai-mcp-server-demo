package com.adeo.mcp.server.demo.service.dto;

import com.adeo.mcp.server.demo.service.dto.enums.IncidentSeverity;
import com.adeo.mcp.server.demo.service.dto.enums.IncidentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Data Transfer Object for Incident entity.
 */
public class IncidentDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("severity")
    private IncidentSeverity severity;
    
    @JsonProperty("status")
    private IncidentStatus status;
    
    @JsonProperty("reporterName")
    private String reporterName;
    
    @JsonProperty("assignedTo")
    private String assignedTo;
    
    @JsonProperty("resolution")
    private String resolution;
    
    @JsonProperty("createdDate")
    private LocalDate createdDate;
    
    @JsonProperty("updatedDate")
    private LocalDate updatedDate;

    // Default constructor
    public IncidentDto() {}

    // Constructor with all fields
    public IncidentDto(Long id, String title, String description, IncidentSeverity severity, 
                      IncidentStatus status, String reporterName, String assignedTo, 
                      String resolution, LocalDate createdDate, LocalDate updatedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.reporterName = reporterName;
        this.assignedTo = assignedTo;
        this.resolution = resolution;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public IncidentSeverity getSeverity() { return severity; }
    public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

    public IncidentStatus getStatus() { return status; }
    public void setStatus(IncidentStatus status) { this.status = status; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public LocalDate getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDate updatedDate) { this.updatedDate = updatedDate; }

    @Override
    public String toString() {
        return "IncidentDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", severity=" + severity +
                ", status=" + status +
                ", reporterName='" + reporterName + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", resolution='" + resolution + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
