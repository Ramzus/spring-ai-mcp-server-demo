package com.adeo.mcp.server.demo.config;

import com.adeo.mcp.server.demo.model.RunTopic;
import com.adeo.mcp.server.demo.repository.RunTopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RunTopicRepository runTopicRepository;

    @Autowired
    public DataInitializer(RunTopicRepository runTopicRepository) {
        this.runTopicRepository = runTopicRepository;
    }

    @Override
    public void run(String... args) {
        // Check if the database is empty
        if (runTopicRepository.count() != 0) {
            logger.info("Database already contains data. Skipping initialization.");
            return;
        }

        logger.info("Database is empty. Initializing with sample data...");

        RunTopic sampleRunTopic = new RunTopic(
                "12341",
                LocalDateTime.parse("2025-05-14T11:00"),
                200,
                "fr",
                Arrays.asList(
                        "Customer not found in Pyxis application",
                        "Hello, can you give me the client number please",
                        "The client number is 10214, the issue seems to concern other clients : 20114, 50001",
                        "All Clients are available in Cassiope but not using Pyxis search"
                ),
                Arrays.asList("mdm", "pyxis"),
                "The problem concerns the impossibility of searching for a client on the Pyxis tool. " +
                        "To solve the problem we had to: " +
                        "- Check the presence of search tokens on the customers concerned. " +
                        "- As the tokens were not present, we had to restart token generation. " +
                        "- Check 30s later for the presence of the search tokens on the clients concerned.",
                "20011383"
        );

        RunTopic savedTopic = runTopicRepository.save(sampleRunTopic);
        logger.info("Sample RunTopic saved: {}", savedTopic);

    }
}
