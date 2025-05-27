package com.adeo.demo.incident.backend.web.controller;

import com.adeo.demo.incident.backend.persistence.IncidentSeverity;
import com.adeo.demo.incident.backend.persistence.IncidentStatus;
import com.adeo.demo.incident.backend.services.IncidentService;
import com.adeo.demo.incident.backend.web.dto.CreateIncidentDto;
import com.adeo.demo.incident.backend.web.dto.IncidentDto;
import com.adeo.demo.incident.backend.web.dto.UpdateIncidentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for managing incidents.
 */
@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class IncidentController {

    private final IncidentService incidentService;

    /**
     * Get all incidents.
     * @return list of all incidents
     */
    @GetMapping
    public ResponseEntity<List<IncidentDto>> getAllIncidents() {
        log.info("GET /api/incidents - Retrieving all incidents");
        List<IncidentDto> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    /**
     * Get current incidents (OPEN or IN_PROGRESS).
     * @return list of current incidents
     */
    @GetMapping("/current")
    public ResponseEntity<List<IncidentDto>> getCurrentIncidents() {
        log.info("GET /api/incidents/current - Retrieving current incidents");
        List<IncidentDto> incidents = incidentService.getCurrentIncidents();
        return ResponseEntity.ok(incidents);
    }

    /**
     * Get incidents by status.
     * @param status the incident status
     * @return list of incidents with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<IncidentDto>> getIncidentsByStatus(@PathVariable IncidentStatus status) {
        log.info("GET /api/incidents/status/{} - Retrieving incidents by status", status);
        List<IncidentDto> incidents = incidentService.getIncidentsByStatus(status);
        return ResponseEntity.ok(incidents);
    }

    /**
     * Get incidents by severity.
     * @param severity the incident severity
     * @return list of incidents with the specified severity
     */
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<IncidentDto>> getIncidentsBySeverity(@PathVariable IncidentSeverity severity) {
        log.info("GET /api/incidents/severity/{} - Retrieving incidents by severity", severity);
        List<IncidentDto> incidents = incidentService.getIncidentsBySeverity(severity);
        return ResponseEntity.ok(incidents);
    }

    /**
     * Get incident by ID.
     * @param id the incident ID
     * @return the incident if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable Long id) {
        log.info("GET /api/incidents/{} - Retrieving incident by ID", id);
        return incidentService.getIncidentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new incident.
     * @param createIncidentDto the incident data
     * @return the created incident
     */
    @PostMapping
    public ResponseEntity<IncidentDto> createIncident(@Valid @RequestBody CreateIncidentDto createIncidentDto) {
        log.info("POST /api/incidents - Creating new incident: {}", createIncidentDto.getTitle());
        IncidentDto createdIncident = incidentService.createIncident(createIncidentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIncident);
    }

    /**
     * Update an existing incident.
     * @param id the incident ID
     * @param updateIncidentDto the updated incident data
     * @return the updated incident if found
     */
    @PutMapping("/{id}")
    public ResponseEntity<IncidentDto> updateIncident(@PathVariable Long id, 
                                                     @Valid @RequestBody UpdateIncidentDto updateIncidentDto) {
        log.info("PUT /api/incidents/{} - Updating incident", id);
        return incidentService.updateIncident(id, updateIncidentDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete an incident by ID.
     * @param id the incident ID
     * @return 204 No Content if deleted, 404 Not Found if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.info("DELETE /api/incidents/{} - Deleting incident", id);
        if (incidentService.deleteIncident(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Health check endpoint.
     * @return simple health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Incident service is healthy");
    }
}
