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
    private final String BASE_URL = "http://localhost:8083/api/incidents";    public List<IncidentDto> getAllIncidents() {
        try {
            IncidentDto[] incidents = restTemplate.getForObject(BASE_URL, IncidentDto[].class);
            if (incidents == null) {
                return List.of();
            }

            return Arrays.asList(incidents);
        } catch (Exception e) {
            throw e;
        }
    }    public List<IncidentDto> getCurrentIncidents() {
        String url = BASE_URL + "/current";

        try {
            IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
            if (incidents == null) {
                return List.of();
            }

            return Arrays.asList(incidents);
        } catch (Exception e) {
            throw e;
        }
    }    public IncidentDto getIncident(Long incidentId) {
        String url = BASE_URL + "/" + incidentId;

        try {
            return restTemplate.getForObject(url, IncidentDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }    public List<IncidentDto> getIncidentsByStatus(IncidentStatus status) {
        String url = BASE_URL + "/status/" + status;

        try {
            IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
            if (incidents == null) {
                return List.of();
            }

            return Arrays.asList(incidents);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<IncidentDto> getIncidentsBySeverity(IncidentSeverity severity) {
        String url = BASE_URL + "/severity/" + severity;

        try {
            IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
            if (incidents == null) {
                return List.of();
            }

            return Arrays.asList(incidents);
        } catch (Exception e) {
            throw e;
        }
    }
}
