package com.adeo.mcp.server.demo.tools;

import com.adeo.mcp.server.demo.model.RunTopic;
import com.adeo.mcp.server.demo.service.RunTopicService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RunTopicTools {

    private final RunTopicService runTopicService;

    @Autowired
    public RunTopicTools(RunTopicService runTopicService) {
        this.runTopicService = runTopicService;
    }

    @Tool(description = "Returns a list of the last resolved tickets with their resolution procedures.")
    List<RunTopic> getLastResolvedTicket(Long id, String name, @ToolParam(description = "Number of tickets to return", required = false) Integer numberOfTickets) {
        System.out.println("Get last " + numberOfTickets + " tickets");
        return runTopicService.findLatestWithResolutionProcedure(numberOfTickets);
    }

    @Tool(description = "Find run topics by ticket ID")
    List<RunTopic> findByTicketId(@ToolParam(description = "The ticket ID to search for") String ticketId) {
        System.out.println("Finding topics for ticket ID: " + ticketId);
        return runTopicService.findByTicketId(ticketId);
    }

    @Tool(description = "Find run topics by business unit")
    List<RunTopic> findByBusinessUnit(@ToolParam(description = "The business unit to search for") String businessUnit) {
        System.out.println("Finding topics for business unit: " + businessUnit);
        return runTopicService.findByBusinessUnit(businessUnit);
    }

    @Tool(description = "Find run topics assigned to a specific person")
    List<RunTopic> findByPersonInCharge(@ToolParam(description = "Name of the person in charge") String personInCharge) {
        System.out.println("Finding topics assigned to: " + personInCharge);
        return runTopicService.findByPersonInCharge(personInCharge);
    }

    @Tool(description = "Find run topics by affected modules")
    List<RunTopic> findByModules(@ToolParam(description = "List of modules to search for") List<String> modules) {
        System.out.println("Finding topics for modules: " + String.join(", ", modules));
        return runTopicService.findByModulesConcerned(modules);
    }

    @Tool(description = "Find run topics within a date range")
    List<RunTopic> findByDateRange(
            @ToolParam(description = "Start date in ISO format (yyyy-MM-ddTHH:mm:ss)") String startDateStr,
            @ToolParam(description = "End date in ISO format (yyyy-MM-ddTHH:mm:ss)") String endDateStr) {
        LocalDateTime startDate = LocalDateTime.parse(startDateStr);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        System.out.println("Finding topics between " + startDate + " and " + endDate);
        return runTopicService.findByDateRange(startDate, endDate);
    }

    @Tool(description = "Find run topics for a specific business unit within a date range")
    List<RunTopic> findByBusinessUnitAndDateRange(
            @ToolParam(description = "The business unit to search for") String businessUnit,
            @ToolParam(description = "Start date in ISO format (yyyy-MM-ddTHH:mm:ss)") String startDateStr,
            @ToolParam(description = "End date in ISO format (yyyy-MM-ddTHH:mm:ss)") String endDateStr) {
        LocalDateTime startDate = LocalDateTime.parse(startDateStr);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        System.out.println("Finding topics for business unit " + businessUnit + " between " + startDate + " and " + endDate);
        return runTopicService.findByBusinessUnitAndDateRange(businessUnit, startDate, endDate);
    }

    @Tool(description = "Get all run topics")
    List<RunTopic> getAllRunTopics() {
        System.out.println("Retrieving all run topics");
        return runTopicService.findAll();
    }

    @Tool(description = "Get a run topic by its ID")
    RunTopic getRunTopicById(@ToolParam(description = "The ID of the run topic") String id) {
        System.out.println("Retrieving run topic with ID: " + id);
        return runTopicService.findById(id)
                .orElseThrow(() -> new RuntimeException("Run topic not found with ID: " + id));
    }

    @Tool(description = "Create or update a run topic")
    RunTopic saveRunTopic(
            @ToolParam(description = "The run topic to save", required = true) RunTopic runTopic) {
        System.out.println("Saving run topic: " + runTopic);
        return runTopicService.save(runTopic);
    }

    @Tool(description = "Delete a run topic by its ID")
    void deleteRunTopic(@ToolParam(description = "The ID of the run topic to delete") String id) {
        System.out.println("Deleting run topic with ID: " + id);
        runTopicService.deleteById(id);
    }
}
