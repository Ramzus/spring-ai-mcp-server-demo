# Incident Management System

A full-stack incident management application built with Vue.js frontend and Spring Boot backend.

## Features

- **List All Incidents**: View all incidents with filtering by status and severity
- **Current Incidents**: View incidents that are currently open or in progress
- **Create Incident**: Create new incidents with title, description, severity, and assignment
- **Incident Detail**: View detailed information about specific incidents
- **Edit Incident**: Update incident information and status
- **Status Management**: Quick actions to change incident status (Open → In Progress → Resolved → Closed)

## Technology Stack

### Backend
- **Spring Boot 3.1.5** - Main framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database
- **Lombok** - Reducing boilerplate code
- **Gradle** - Build tool

### Frontend
- **Vue.js 3** - Frontend framework
- **Vue Router** - Client-side routing
- **Bootstrap 5** - UI components and styling
- **Axios** - HTTP client
- **Font Awesome** - Icons

## Project Structure

```
incident-app/
├── back/                           # Backend (Spring Boot)
│   ├── src/main/java/
│   │   └── com/adeo/demo/incident/backend/
│   │       ├── IncidentBackendApplication.java
│   │       ├── persistence/        # Entities and repositories
│   │       ├── services/          # Business logic
│   │       └── web/              # Controllers and DTOs
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data.sql              # Sample data
│   └── build.gradle
└── front/                         # Frontend (Vue.js)
    ├── src/
    │   ├── components/           # Vue components
    │   ├── services/            # API services
    │   ├── router/              # Vue Router configuration
    │   ├── App.vue
    │   └── main.js
    ├── package.json
    └── vue.config.js
```

## Getting Started

### Prerequisites
- Java 17 or later
- Node.js 16 or later
- npm or yarn

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd back
   ```

2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

3. The backend will start on `http://localhost:8083`

4. H2 Console is available at `http://localhost:8083/h2-console`
   - JDBC URL: `jdbc:h2:mem:incidentdb`
   - Username: `sa`
   - Password: `password`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd front
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run serve
   ```

4. The frontend will start on `http://localhost:8084`

## API Endpoints

### Incidents
- `GET /api/incidents` - Get all incidents
- `GET /api/incidents/current` - Get current incidents (OPEN or IN_PROGRESS)
- `GET /api/incidents/{id}` - Get incident by ID
- `GET /api/incidents/status/{status}` - Get incidents by status
- `GET /api/incidents/severity/{severity}` - Get incidents by severity
- `POST /api/incidents` - Create new incident
- `PUT /api/incidents/{id}` - Update incident
- `DELETE /api/incidents/{id}` - Delete incident
- `GET /api/incidents/health` - Health check

### Incident Status Values
- `OPEN` - Newly created incident
- `IN_PROGRESS` - Incident is being worked on
- `RESOLVED` - Incident has been resolved
- `CLOSED` - Incident is closed

### Incident Severity Values
- `LOW` - Low priority incident
- `MEDIUM` - Medium priority incident
- `HIGH` - High priority incident
- `CRITICAL` - Critical priority incident

## Sample Data

The application comes with 5 sample incidents covering different severities and statuses:

1. Server Performance Issue (High/Open) - No resolution
2. Login Authentication Problem (Critical/In Progress) - Investigating root cause
3. Database Connection Timeout (Medium/Resolved) - Increased connection pool size and optimized queries  
4. Email Service Down (High/Open) - No resolution
5. UI Display Bug (Low/Closed) - Fixed CSS media queries for mobile responsiveness

### Incident Fields

Each incident includes the following fields:
- **ID**: Unique identifier (auto-generated)
- **Title**: Brief description of the incident (required, max 255 chars)
- **Description**: Detailed description (optional, max 1000 chars)
- **Severity**: Priority level (LOW, MEDIUM, HIGH, CRITICAL)
- **Status**: Current state (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
- **Reporter Name**: Person who reported the incident (required, max 100 chars)
- **Assigned To**: Person or team assigned to resolve (optional, max 100 chars)
- **Resolution**: Description of how the incident was resolved (optional, max 1000 chars)
- **Created Date**: When the incident was created
- **Updated Date**: When the incident was last modified

## Development

### Backend Development
- The backend uses Spring Boot with auto-restart enabled
- H2 database is recreated on each restart with sample data
- Logs are available in the console

### Frontend Development
- Hot reload is enabled for Vue.js development
- API calls are proxied to the backend during development
- Bootstrap 5 is used for responsive design

## Building for Production

### Backend
```bash
cd back
./gradlew build
```

The JAR file will be created in `build/libs/`

### Frontend
```bash
cd front
npm run build
```

The built files will be in the `dist/` directory

## Configuration

### Backend Configuration
- Port: 8083 (configurable in `application.properties`)
- Database: H2 in-memory (configurable for other databases)
- CORS: Enabled for all origins during development

### Frontend Configuration
- Port: 8084 (configurable in `vue.config.js`)
- API Base URL: Configurable in `.env` file

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test your changes
5. Submit a pull request

## License

This project is part of the agentic-ai-demo and is for demonstration purposes.
