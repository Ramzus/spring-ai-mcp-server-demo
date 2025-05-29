package com.adeo.mcp.server.demo.service;

import com.adeo.mcp.server.demo.service.dto.CreateIncidentDto;
import com.adeo.mcp.server.demo.service.dto.IncidentDto;
import com.adeo.mcp.server.demo.service.dto.enums.IncidentSeverity;
import com.adeo.mcp.server.demo.service.dto.enums.IncidentStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IncidentAppService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8083/api/incidents";

    public List<IncidentDto> getAllIncidents() {
        System.out.println("Fetching all incidents from the incident backend service");

        try {
            IncidentDto[] incidents = restTemplate.getForObject(BASE_URL, IncidentDto[].class);
            if (incidents == null) {
                System.out.println("Received null response from incident service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + incidents.length + " incidents from backend");
            return Arrays.asList(incidents);
        } catch (Exception e) {
            System.out.println("Error fetching incidents from backend service: " + e.getMessage());
            throw e;
        }
    }

    public List<IncidentDto> getCurrentIncidents() {
        System.out.println("Fetching current incidents from the incident backend service");
        String url = BASE_URL + "/current";

        try {
            IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
            if (incidents == null) {
                System.out.println("Received null response from incident service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + incidents.length + " current incidents from backend");
            return Arrays.asList(incidents);
        } catch (Exception e) {
            System.out.println("Error fetching current incidents from backend service: " + e.getMessage());
            throw e;
        }
    }

    public IncidentDto getIncident(Long incidentId) {
        System.out.println("Fetching incident with ID: " + incidentId);
        String url = BASE_URL + "/" + incidentId;

        try {
            return restTemplate.getForObject(url, IncidentDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Incident not found with ID: " + incidentId);
            return null;
        } catch (Exception e) {
            System.out.println("Error fetching incident with ID " + incidentId + ": " + e.getMessage());
            throw e;
        }
    }

    public List<IncidentDto> getIncidentsByStatus(IncidentStatus status) {
        System.out.println("Fetching incidents with status: " + status);
        String url = BASE_URL + "/status/" + status;

        try {
            IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
            if (incidents == null) {
                System.out.println("Received null response from incident service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + incidents.length + " incidents with status " + status);
            return Arrays.asList(incidents);
        } catch (Exception e) {
            System.out.println("Error fetching incidents by status " + status + ": " + e.getMessage());
            throw e;
        }
    }

    public List<IncidentDto> getIncidentsBySeverity(IncidentSeverity severity) {
        System.out.println("Fetching incidents with severity: " + severity);
        String url = BASE_URL + "/severity/" + severity;

        try {
            IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
            if (incidents == null) {
                System.out.println("Received null response from incident service");
                return List.of();
            }

            System.out.println("Successfully retrieved " + incidents.length + " incidents with severity " + severity);
            return Arrays.asList(incidents);
        } catch (Exception e) {
            System.out.println("Error fetching incidents by severity " + severity + ": " + e.getMessage());
            throw e;
        }
    }
}
