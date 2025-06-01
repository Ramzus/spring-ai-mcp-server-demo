package com.example.oms.mcp.server.service;

import com.example.oms.mcp.server.service.dto.IncidentDto;
import com.example.oms.mcp.server.service.dto.enums.IncidentSeverity;
import com.example.oms.mcp.server.service.dto.enums.IncidentStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IncidentAppService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8083/api/incidents";

    public IncidentAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<IncidentDto> getAllIncidents() {
        IncidentDto[] incidents = restTemplate.getForObject(BASE_URL, IncidentDto[].class);
        if (incidents == null) {
            return List.of();
        }

        return Arrays.asList(incidents);
    }

    public List<IncidentDto> getCurrentIncidents() {
        String url = BASE_URL + "/current";

        IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
        if (incidents == null) {
            return List.of();
        }

        return Arrays.asList(incidents);
    }

    public IncidentDto getIncident(Long incidentId) {
        String url = BASE_URL + "/" + incidentId;

        try {
            return restTemplate.getForObject(url, IncidentDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public List<IncidentDto> getIncidentsByStatus(IncidentStatus status) {
        String url = BASE_URL + "/status/" + status;

        IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
        if (incidents == null) {
            return List.of();
        }

        return Arrays.asList(incidents);
    }

    public List<IncidentDto> getIncidentsBySeverity(IncidentSeverity severity) {
        String url = BASE_URL + "/severity/" + severity;

        IncidentDto[] incidents = restTemplate.getForObject(url, IncidentDto[].class);
        if (incidents == null) {
            return List.of();
        }

        return Arrays.asList(incidents);
    }
}
