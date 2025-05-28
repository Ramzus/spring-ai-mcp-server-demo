package com.adeo.mcp.server.demo.controller;

import com.adeo.mcp.server.demo.model.RunTopic;
import com.adeo.mcp.server.demo.service.RunTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/run-topics")
public class RunTopicController {

    private final RunTopicService runTopicService;

    @Autowired
    public RunTopicController(RunTopicService runTopicService) {
        this.runTopicService = runTopicService;
    }

    @GetMapping
    public List<RunTopic> getAllRunTopics() {
        return runTopicService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunTopic> getRunTopicById(@PathVariable String id) {
        return runTopicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RunTopic createRunTopic(@RequestBody RunTopic runTopic) {
        return runTopicService.save(runTopic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunTopic> updateRunTopic(@PathVariable String id, @RequestBody RunTopic runTopic) {
        return runTopicService.findById(id)
                .map(existingRunTopic -> {
                    runTopic.setId(id);
                    return ResponseEntity.ok(runTopicService.save(runTopic));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRunTopic(@PathVariable String id) {
        return runTopicService.findById(id)
                .map(runTopic -> {
                    runTopicService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ticket/{ticketId}")
    public List<RunTopic> getRunTopicsByTicketId(@PathVariable String ticketId) {
        return runTopicService.findByTicketId(ticketId);
    }

    @GetMapping("/business-unit/{businessUnit}")
    public List<RunTopic> getRunTopicsByBusinessUnit(@PathVariable String businessUnit) {
        return runTopicService.findByBusinessUnit(businessUnit);
    }

    @GetMapping("/person/{personInCharge}")
    public List<RunTopic> getRunTopicsByPersonInCharge(@PathVariable String personInCharge) {
        return runTopicService.findByPersonInCharge(personInCharge);
    }

    @GetMapping("/modules")
    public List<RunTopic> getRunTopicsByModules(@RequestParam List<String> modules) {
        return runTopicService.findByModulesConcerned(modules);
    }

    @GetMapping("/date-range")
    public List<RunTopic> getRunTopicsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return runTopicService.findByDateRange(start, end);
    }

    @GetMapping("/business-unit/{businessUnit}/date-range")
    public List<RunTopic> getRunTopicsByBusinessUnitAndDateRange(
            @PathVariable String businessUnit,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return runTopicService.findByBusinessUnitAndDateRange(businessUnit, start, end);
    }
    
    /**
     * Retrieves the latest RunTopics with non-empty resolution procedures
     * 
     * @param limit The maximum number of entries to return (defaults to 5)
     * @return List of RunTopic objects with non-empty resolution procedures, ordered by date (most recent first)
     */
    @GetMapping("/latest-resolved")
    public List<RunTopic> getLatestWithResolutionProcedure(@RequestParam(defaultValue = "5") int limit) {
        return runTopicService.findLatestWithResolutionProcedure(limit);
    }
}
