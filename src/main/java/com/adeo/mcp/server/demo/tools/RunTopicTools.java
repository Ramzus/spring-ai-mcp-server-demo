package com.adeo.mcp.server.demo.tools;

import com.adeo.mcp.server.demo.model.RunTopic;
import com.adeo.mcp.server.demo.service.RunTopicService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println("Get last " + numberOfTickets + "tickets");
        return runTopicService.findLatestWithResolutionProcedure(numberOfTickets);
    }

}
