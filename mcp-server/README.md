# Spring AI MCP Server Demo

This project demonstrates how to set up and use a Spring AI Model Context Protocol (MCP) server.

## Overview

This application provides a Spring Boot-based MCP server implementation that allows Claude AI desktop application to communicate with business data and logic through a standardized protocol.

## Prerequisites

- Java 17
- Docker and Docker Compose
- MCP Client Application (Claude AI desktop application for example)

## Building the Project

Build the project using Gradle:

```bash
./gradlew clean build
```

This will generate the JAR file at `build/libs/mcp-server-demo-0.0.1-SNAPSHOT.jar`

## Database Setup

This project requires a MongoDB database which is automatically set up using Docker Compose:

1. Ensure Docker and Docker Compose are properly installed and running on your system
2. Start the MongoDB database by running:
   ```bash
   docker-compose up
   ```
3. The database will be automatically populated with sample data during the first server execution

**Note:** Always start the MongoDB database with `docker-compose up` before launching the MCP Client (Claude Desktop application).

## Configuration (Windows User)

To use the MCP Server with Claude AI, you need to update the Claude desktop configuration file.

1. Locate the Claude configuration file at : 
   ```
   C:\Users\YOUR_USERNAME\AppData\Roaming\Claude\claude_desktop_config.json
   ```

2. Update the file with the following configuration:
   ```json
   {
     "mcpServers": {
       "runAIDemo": {
         "command": "java",
         "args": [
           "-Dspring.ai.mcp.server.stdio=true",
           "-Dspring.main.web-application-type=none",
           "-Dlogging.pattern.console=",
           "-jar",
           "YOUR_PROJECT_PATH\\build\\libs\\mcp-server-demo-0.0.1-SNAPSHOT.jar"
         ]
       }
     }
   }
   ```

   **Important:** Replace `YOUR_PROJECT_PATH` with the absolute path to your project directory.

## Running the Server

1. Start the MongoDB database with `docker-compose up` and keep it running
2. Once the database is running and configured, you can launch the Claude AI desktop application
3. Claude desktop will automatically start and use the MCP server
4. You can ask some questions like : ```"Can you give me the resolution procedure of the last run ticket ?"```

## Key Features

- Spring AI integration
- AI + API Services for Adeo Run business context. 
- Model Context Protocol (MCP) server implementation
- Automatic sample data generation for MongoDB
- Docker-based database setup for easy deployment

## Troubleshooting

If you encounter issues:

1. Verify the path in your configuration is correct
2. Ensure Java is available in your system PATH
3. Check that the JAR file was built correctly
4. Confirm that Docker and MongoDB are running properly (`docker ps` command)
5. Review Claude AI logs for any error messages
6. Check MongoDB connection parameters if database connectivity issues occur

## Further Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Claude AI Documentation](https://docs.anthropic.com/)
- [MongoDB Documentation](https://www.mongodb.com/docs/)
- [Docker Documentation](https://docs.docker.com/)

