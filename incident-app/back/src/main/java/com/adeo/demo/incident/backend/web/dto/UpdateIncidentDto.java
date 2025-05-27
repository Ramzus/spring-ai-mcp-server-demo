package com.adeo.demo.incident.backend.web.dto;

import com.adeo.demo.incident.backend.persistence.IncidentSeverity;
import com.adeo.demo.incident.backend.persistence.IncidentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating existing incidents.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIncidentDto {
    
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    private IncidentSeverity severity;
    
    private IncidentStatus status;
      @Size(max = 100, message = "Assigned to must not exceed 100 characters")
    private String assignedTo;
    
    @Size(max = 1000, message = "Resolution must not exceed 1000 characters")
    private String resolution;
}
