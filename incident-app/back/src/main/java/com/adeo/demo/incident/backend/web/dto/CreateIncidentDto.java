package com.adeo.demo.incident.backend.web.dto;

import com.adeo.demo.incident.backend.persistence.IncidentSeverity;
import com.adeo.demo.incident.backend.persistence.IncidentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating new incidents.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncidentDto {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Severity is required")
    private IncidentSeverity severity;
    
    @NotBlank(message = "Reporter name is required")
    @Size(max = 100, message = "Reporter name must not exceed 100 characters")
    private String reporterName;
      @Size(max = 100, message = "Assigned to must not exceed 100 characters")
    private String assignedTo;
    
    @Size(max = 1000, message = "Resolution must not exceed 1000 characters")
    private String resolution;
}
