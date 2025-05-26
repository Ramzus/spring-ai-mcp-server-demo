package com.adeo.demo.incident.backend.services;

import com.adeo.demo.incident.backend.persistence.Incident;
import com.adeo.demo.incident.backend.persistence.IncidentRepository;
import com.adeo.demo.incident.backend.persistence.IncidentSeverity;
import com.adeo.demo.incident.backend.persistence.IncidentStatus;
import com.adeo.demo.incident.backend.web.dto.CreateIncidentDto;
import com.adeo.demo.incident.backend.web.dto.IncidentDto;
import com.adeo.demo.incident.backend.web.dto.UpdateIncidentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing incidents.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepository;

    /**
     * Get all incidents.
     * @return list of all incidents
     */
    @Transactional(readOnly = true)
    public List<IncidentDto> getAllIncidents() {
        log.info("Retrieving all incidents");
        return incidentRepository.findAllOrderBySeverity()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get current incidents (OPEN or IN_PROGRESS).
     * @return list of current incidents
     */
    @Transactional(readOnly = true)
    public List<IncidentDto> getCurrentIncidents() {
        log.info("Retrieving current incidents");
        return incidentRepository.findCurrentIncidents()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get incidents by status.
     * @param status the incident status
     * @return list of incidents with the specified status
     */
    @Transactional(readOnly = true)
    public List<IncidentDto> getIncidentsByStatus(IncidentStatus status) {
        log.info("Retrieving incidents with status: {}", status);
        return incidentRepository.findByStatus(status)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get incidents by severity.
     * @param severity the incident severity
     * @return list of incidents with the specified severity
     */
    @Transactional(readOnly = true)
    public List<IncidentDto> getIncidentsBySeverity(IncidentSeverity severity) {
        log.info("Retrieving incidents with severity: {}", severity);
        return incidentRepository.findBySeverity(severity)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get incident by ID.
     * @param id the incident ID
     * @return the incident if found
     */
    @Transactional(readOnly = true)
    public Optional<IncidentDto> getIncidentById(Long id) {
        log.info("Retrieving incident with ID: {}", id);
        return incidentRepository.findById(id)
                .map(this::convertToDto);
    }

    /**
     * Create a new incident.
     * @param createIncidentDto the incident data
     * @return the created incident
     */
    public IncidentDto createIncident(CreateIncidentDto createIncidentDto) {
        log.info("Creating new incident: {}", createIncidentDto.getTitle());
        
        Incident incident = new Incident();
        incident.setTitle(createIncidentDto.getTitle());
        incident.setDescription(createIncidentDto.getDescription());
        incident.setSeverity(createIncidentDto.getSeverity());
        incident.setStatus(IncidentStatus.OPEN); // New incidents are always OPEN
        incident.setReporterName(createIncidentDto.getReporterName());
        incident.setAssignedTo(createIncidentDto.getAssignedTo());
        incident.setCreatedDate(LocalDate.now());
        incident.setUpdatedDate(LocalDate.now());

        Incident savedIncident = incidentRepository.save(incident);
        log.info("Created incident with ID: {}", savedIncident.getId());
        
        return convertToDto(savedIncident);
    }

    /**
     * Update an existing incident.
     * @param id the incident ID
     * @param updateIncidentDto the updated incident data
     * @return the updated incident if found
     */
    public Optional<IncidentDto> updateIncident(Long id, UpdateIncidentDto updateIncidentDto) {
        log.info("Updating incident with ID: {}", id);
        
        return incidentRepository.findById(id)
                .map(incident -> {
                    if (updateIncidentDto.getTitle() != null) {
                        incident.setTitle(updateIncidentDto.getTitle());
                    }
                    if (updateIncidentDto.getDescription() != null) {
                        incident.setDescription(updateIncidentDto.getDescription());
                    }
                    if (updateIncidentDto.getSeverity() != null) {
                        incident.setSeverity(updateIncidentDto.getSeverity());
                    }
                    if (updateIncidentDto.getStatus() != null) {
                        incident.setStatus(updateIncidentDto.getStatus());
                    }
                    if (updateIncidentDto.getAssignedTo() != null) {
                        incident.setAssignedTo(updateIncidentDto.getAssignedTo());
                    }
                    incident.setUpdatedDate(LocalDate.now());

                    Incident savedIncident = incidentRepository.save(incident);
                    log.info("Updated incident with ID: {}", savedIncident.getId());
                    
                    return convertToDto(savedIncident);
                });
    }

    /**
     * Delete an incident by ID.
     * @param id the incident ID
     * @return true if the incident was deleted, false if not found
     */
    public boolean deleteIncident(Long id) {
        log.info("Deleting incident with ID: {}", id);
        
        if (incidentRepository.existsById(id)) {
            incidentRepository.deleteById(id);
            log.info("Deleted incident with ID: {}", id);
            return true;
        }
        
        log.warn("Incident with ID {} not found for deletion", id);
        return false;
    }

    /**
     * Convert Incident entity to IncidentDto.
     * @param incident the incident entity
     * @return the incident DTO
     */
    private IncidentDto convertToDto(Incident incident) {
        return new IncidentDto(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getReporterName(),
                incident.getAssignedTo(),
                incident.getCreatedDate(),
                incident.getUpdatedDate()
        );
    }
}
