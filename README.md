# Agentic AI Demo - Microservices Platform

This demo showcases a complete microservices architecture with a Model Context Protocol (MCP) server for agentic AI applications.

## 🎯 Project Purpose

This project demonstrates a modern microservices platform featuring:
- **Order Management**: Complete order lifecycle management with status progression
- **Payment Processing**: Secure payment handling and transaction tracking
- **Incident Management**: Issue tracking and resolution workflow
- **AI Integration**: MCP server for intelligent automation and context-aware operations

The platform serves as a reference implementation for building scalable, containerized business applications with AI capabilities.

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Order App     │    │  Payment App    │    │  Incident App   │
│                 │    │                 │    │                 │
│ Frontend:5173   │    │ Frontend:5174   │    │ Frontend:5175   │
│ Backend: 8081   │    │ Backend: 8082   │    │ Backend: 8083   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │   MCP Server    │
                    │   Port: 8085    │
                    └─────────────────┘
                                 │
                    ┌─────────────────┐
                    │    MongoDB      │
                    │   Port: 27017   │
                    └─────────────────┘
```

## 🛠️ Technologies Used

### Frontend
- **Vue.js 3**: Modern reactive frontend framework
- **Vite**: Fast build tool and development server
- **TypeScript**: Type-safe JavaScript development
- **CSS3**: Modern styling with animations and responsive design

### Backend
- **Spring Boot**: Enterprise-grade Java framework
- **H2 Database**: Embedded database for development
- **MongoDB**: Document database for MCP server
- **Gradle**: Build automation and dependency management

### Infrastructure
- **Docker & Docker Compose**: Containerization and orchestration
- **ADEO Certificates**: Enterprise security integration
- **Health Checks**: Service monitoring and reliability
- **Multi-stage Builds**: Optimized container images

### APIs & Integration
- **REST APIs**: Standard HTTP-based service communication
- **Model Context Protocol (MCP)**: AI-agent integration standard
- **Spring Boot Actuator**: Production-ready monitoring endpoints

## 🚀 How to Launch with Docker Compose

### Prerequisites
- Docker and Docker Compose installed
- Ports 5173-5175, 8081-8083, 8085, and 27017 available

### Quick Start
```bash
# Clone and navigate to the project
cd /path/to/agentic-ai-demo

# Build and start all services
docker-compose up --build -d

# Check service status
docker-compose ps

# View logs
docker-compose logs -f
```

### Access the Applications

#### User Interfaces
- **Order Management**: http://localhost:5173
- **Payment Management**: http://localhost:5174
- **Incident Management**: http://localhost:5175

#### API Endpoints
- **Orders API**: http://localhost:8081
  - Health Check: http://localhost:8081/actuator/health
  - Swagger UI: http://localhost:8081/swagger-ui.html
- **Payments API**: http://localhost:8082
  - Health Check: http://localhost:8082/actuator/health
- **Incidents API**: http://localhost:8083
  - Health Check: http://localhost:8083/actuator/health

#### Infrastructure Services
- **MCP Server**: http://localhost:8085
  - Health Check: http://localhost:8085/health
- **MongoDB**: localhost:27017 (database: mcp-demo)

### Useful Commands

```bash
# Stop all services
docker-compose down

# Rebuild specific service
docker-compose up --build -d order-backend

# View specific service logs
docker-compose logs -f [service-name]

# Clean up (remove containers and volumes)
docker-compose down --volumes

# Complete cleanup (including images)
docker-compose down --volumes --rmi all
```

### Health Monitoring
All services include automatic health checks:
- Check interval: 30 seconds
- Retries: 3 attempts before marking as unhealthy
- Timeout: 10 seconds per check

## 🔧 Configuration

The platform uses environment-based configuration:

```yaml
# Backend service URLs
ORDER_SERVICE_BASE_URL=http://order-backend:8081
PAYMENT_SERVICE_BASE_URL=http://payment-backend:8082
INCIDENT_SERVICE_BASE_URL=http://incident-backend:8083

# MongoDB connection for MCP Server
MONGODB_URI=mongodb://mongo:27017/mcp-demo

# Frontend API configuration
VITE_API_URL=http://[service]-backend:[port]
```

## 📊 Sample Data

The platform comes with pre-configured sample data:
- **Orders**: 8 sample orders with various statuses (CREATED → PENDING → SHIPPED → DELIVERED → FINISHED)
- **Payments**: 4 payment records with different statuses (COMPLETED, PENDING, FAILED)
- **Incidents**: 5 incident tickets with various severity levels and resolution states
