package com.adeo.demo.incident.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Incident entities.
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    /**
     * Find incidents by status.
     * @param status the incident status
     * @return list of incidents with the specified status
     */
    List<Incident> findByStatus(IncidentStatus status);

    /**
     * Find incidents by severity.
     * @param severity the incident severity
     * @return list of incidents with the specified severity
     */
    List<Incident> findBySeverity(IncidentSeverity severity);

    /**
     * Find incidents assigned to a specific person or team.
     * @param assignedTo the assigned person or team
     * @return list of incidents assigned to the specified person/team
     */
    List<Incident> findByAssignedTo(String assignedTo);

    /**
     * Find current open incidents (status is OPEN or IN_PROGRESS).
     * @return list of current open incidents
     */
    @Query("SELECT i FROM Incident i WHERE i.status = 'OPEN' OR i.status = 'IN_PROGRESS'")
    List<Incident> findCurrentIncidents();

    /**
     * Find incidents ordered by severity (CRITICAL first, then HIGH, MEDIUM, LOW).
     * @return list of incidents ordered by severity
     */
    @Query("SELECT i FROM Incident i ORDER BY " +
           "CASE i.severity " +
           "WHEN 'CRITICAL' THEN 1 " +
           "WHEN 'HIGH' THEN 2 " +
           "WHEN 'MEDIUM' THEN 3 " +
           "WHEN 'LOW' THEN 4 " +
           "END, i.createdDate DESC")
    List<Incident> findAllOrderBySeverity();
}
