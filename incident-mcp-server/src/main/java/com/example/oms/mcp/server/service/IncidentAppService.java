package com.example.oms.mcp.server.service;

import com.example.oms.mcp.server.service.dto.IncidentDto;
import com.example.oms.mcp.server.service.dto.enums.IncidentSeverity;
import com.example.oms.mcp.server.service.dto.enums.IncidentStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<List<IncidentDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<IncidentDto> incidents = response.getBody();

        return incidents;
    }

    public List<IncidentDto> getCurrentIncidents() {
        String url = BASE_URL + "/current";

        ResponseEntity<List<IncidentDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<IncidentDto> incidents = response.getBody();

        return incidents;
    }

    public IncidentDto getIncident(Long incidentId) {
        String url = BASE_URL + "/" + incidentId;

        try {
            ResponseEntity<IncidentDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    IncidentDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public List<IncidentDto> getIncidentsByStatus(IncidentStatus status) {
        String url = BASE_URL + "/status/" + status;

        ResponseEntity<List<IncidentDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<IncidentDto> incidents = response.getBody();

        return incidents;
    }

    public List<IncidentDto> getIncidentsBySeverity(IncidentSeverity severity) {
        String url = BASE_URL + "/severity/" + severity;

        ResponseEntity<List<IncidentDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<IncidentDto> incidents = response.getBody();

        return incidents;
    }

    public IncidentDto createIncident(IncidentDto incidentDto) {
        ResponseEntity<IncidentDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(incidentDto),
                IncidentDto.class
        );
        return response.getBody();
    }

    public IncidentDto updateIncident(Long incidentId, IncidentDto incidentDto) {
        String url = BASE_URL + "/" + incidentId;
        incidentDto.setId(incidentId);

        try {
            ResponseEntity<IncidentDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(incidentDto),
                    IncidentDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
