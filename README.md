# 🚀 Agentic AI Demo - Smart Business Platform

> **Transform your business operations with AI-powered automation!**

Welcome to a cutting-edge demonstration of how AI agents can seamlessly integrate with real business applications. This platform showcases a complete microservices ecosystem where AI assistants (like Claude or GitHub Copilot) can directly manage orders, process payments, and handle incidents through natural conversation.

## ✨ What Makes This Special?

🤖 **Talk to Your Business System**: Use natural language to manage orders, payments, and incidents  
⚡ **Real-Time Operations**: AI agents perform actual business operations, not just mock data  
🔧 **Production-Ready**: Built with enterprise-grade technologies and best practices  
🎯 **Plug & Play**: Easy setup with Docker - get running in minutes  
🌐 **Modern Stack**: Vue.js frontends, Spring Boot APIs, and intelligent MCP servers

## 📋 Quick Overview

| Service | What It Does | Frontend | API |
|---------|-------------|----------|-----|
| 📦 **Orders** | Manage customer orders & lifecycle | [localhost:5173](http://localhost:5173) | [localhost:8081](http://localhost:8081) |
| 💳 **Payments** | Process payments & transactions | [localhost:5174](http://localhost:5174) | [localhost:8082](http://localhost:8082) |
| 🚨 **Incidents** | Track issues & resolutions | [localhost:5175](http://localhost:5175) | [localhost:8083](http://localhost:8083) |
| 🧠 **AI Assistant** | Smart automation layer | - | [localhost:8085](http://localhost:8085) |

## 🏗️ System Architecture

```
    🖥️ Frontend Apps (Vue.js + TypeScript)
    ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
    │   Orders    │  │  Payments   │  │  Incidents  │
    │   :5173     │  │   :5174     │  │   :5175     │
    └─────────────┘  └─────────────┘  └─────────────┘
           │                │               │
    ⚡ REST APIs (Spring Boot + Java)
    ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
    │ Order API   │  │Payment API  │  │Incident API │
    │   :8081     │  │   :8082     │  │   :8083     │
    └─────────────┘  └─────────────┘  └─────────────┘
           │                │               │
           └────────────────┼───────────────┘
                           │
    🤖 AI Integration Layer
           ┌─────────────────────────┐
           │     MCP Servers         │
           │   (AI ↔ Business)       │
           │       :8085             │
           └─────────────────────────┘
                           │
    💾 Data Layer
           ┌─────────────────────────┐
           │      MongoDB            │
           │   (Document Store)      │
           │       :27017            │
           └─────────────────────────┘
```

## 🚀 Get Started in 3 Minutes!

### Prerequisites ✅
- [Docker Desktop](https://www.docker.com/products/docker-desktop) installed and running
- Available ports: `5173-5175`, `8081-8083`, `8085`, `27017`

### 1️⃣ Launch Everything
```bash
# Navigate to project directory
cd agentic-ai-demo

# 🚀 Start all services (this might take 2-3 minutes first time)
docker-compose up --build -d

# ✅ Check everything is running
docker-compose ps
```

### 2️⃣ Verify It's Working
Open these links to confirm services are up:
- 🌐 [Order Management UI](http://localhost:5173) - Manage customer orders
- 💳 [Payment Portal](http://localhost:5174) - Handle transactions  
- 🎯 [Incident Dashboard](http://localhost:5175) - Track issues
- 📊 [Order API Health](http://localhost:8081/actuator/health) - Should show `{"status":"UP"}`

### 3️⃣ Explore Sample Data
The platform comes pre-loaded with realistic business data:
- **8 sample orders** in various stages (new → processing → shipped → delivered)
- **4 payment records** with different statuses 
- **5 incident tickets** with varying priorities

> 🎉 **You're ready!** Your complete business platform is now running!

## 🛠️ Management Commands

### Essential Operations
```bash
# 🛑 Stop everything
docker-compose down

# 🔄 Restart a specific service  
docker-compose restart order-backend

# 📜 View logs for debugging
docker-compose logs -f [service-name]

# 🧹 Clean slate (removes all data)
docker-compose down --volumes

# 🗑️ Complete cleanup (removes images too)
docker-compose down --volumes --rmi all
```

### Health Monitoring
All services include smart health checks:
- ✅ **Auto-healing**: Unhealthy containers restart automatically  
- ⏱️ **Quick detection**: Issues detected within 30 seconds
- 🔄 **Retry logic**: 3 attempts before marking as failed

Check health status: `docker-compose ps`

## 🧰 Technology Stack

<details>
<summary><strong>🖥️ Frontend Technologies</strong></summary>

- **Vue.js 3** - Modern reactive framework with Composition API
- **TypeScript** - Type-safe development with excellent IDE support  
- **Vite** - Lightning-fast build tool and hot reload
- **CSS3** - Modern styling with responsive design

</details>

<details>
<summary><strong>⚙️ Backend Technologies</strong></summary>

- **Spring Boot** - Enterprise Java framework with auto-configuration
- **H2 Database** - Embedded SQL database for rapid development
- **MongoDB** - Document database for flexible data structures
- **Gradle** - Advanced build automation and dependency management

</details>

<details>
<summary><strong>🚀 Infrastructure & DevOps</strong></summary>

- **Docker** - Containerization for consistent environments
- **Docker Compose** - Multi-container orchestration
- **Health Checks** - Built-in monitoring and auto-recovery
- **Multi-stage Builds** - Optimized container images

</details>

<details>
<summary><strong>🤖 AI Integration</strong></summary>

- **Model Context Protocol (MCP)** - Standard for AI-app integration
- **REST APIs** - HTTP-based service communication
- **Spring Boot Actuator** - Production monitoring endpoints

</details>

## 🤖 Connect Your AI Assistant

The real magic happens when you connect AI assistants to control your business operations through conversation!

### 🔧 Quick Setup for VS Code

1. **Build the MCP servers** (one-time setup):
```bash
# Build both MCP server JAR files
./gradlew :mcp-servers:oms-mcp-server:build
./gradlew :mcp-servers:incident-mcp-server:build
```

2. **Configure VS Code** - Add to your `settings.json`:
```json
{
  "mcp": {
    "servers": {
      "oms-mcp-server": {
        "type": "stdio",
        "command": "java",
        "args": ["-jar", "YOUR_PROJECT_PATH/mcp-servers/oms-mcp-server/build/libs/oms-mcp-server-0.0.1-SNAPSHOT.jar"]
      },
      "incident-mcp-server": {
        "type": "stdio", 
        "command": "java",
        "args": ["-jar", "YOUR_PROJECT_PATH/mcp-servers/incident-mcp-server/build/libs/incident-mcp-server-0.0.1-SNAPSHOT.jar"]
      }
    }
  },
  "chat.mcp.discovery.enabled": true
}
```

3. **Replace `YOUR_PROJECT_PATH`** with your actual project path.

### 💬 Talk to Your Business System

Once connected, try these natural language commands:

| What You Say | What Happens |
|-------------|-------------|
| *"Show me all pending orders"* | 📋 Lists orders waiting to be processed |
| *"Create a payment for order #1001 for $150"* | 💳 Processes a new payment transaction |
| *"What high-priority incidents are open?"* | 🚨 Filters critical issues needing attention |
| *"Move order #1001 to the next stage"* | ⚡ Advances order through workflow |
| *"Show me all failed payments"* | 💸 Displays transactions that need retry |

> 💡 **Pro Tip**: The AI maintains full business context and validates operations, so you can have natural conversations about complex business workflows!

## ❓ Troubleshooting

<details>
<summary><strong>🚨 Services won't start</strong></summary>

**Problem**: `docker-compose up` fails or services show as unhealthy

**Solutions**:
```bash
# Check if ports are already in use
lsof -i :5173 -i :8081 -i :8082 -i :8083 -i :8085 -i :27017

# Clean up any existing containers
docker-compose down --volumes
docker system prune -f

# Restart Docker Desktop and try again
docker-compose up --build -d
```

</details>

<details>
<summary><strong>🔌 Can't connect to AI assistants</strong></summary>

**Problem**: MCP servers not recognized by VS Code or Claude

**Solutions**:
1. **Verify JAR files exist**:
   ```bash
   ls -la mcp-servers/*/build/libs/*.jar
   ```

2. **Rebuild if missing**:
   ```bash
   ./gradlew :mcp-servers:oms-mcp-server:build
   ./gradlew :mcp-servers:incident-mcp-server:build
   ```

3. **Check VS Code settings path** - Use absolute paths in settings.json
4. **Restart VS Code** after configuration changes

</details>

<details>
<summary><strong>💾 Data not persisting</strong></summary>

**Problem**: Sample data disappears after restart

**Solution**: This is expected behavior. The H2 databases are in-memory for demo purposes. MongoDB data persists in Docker volumes.

</details>

## 🎯 What's Next?

Ready to extend this platform? Here are some ideas:

- 🔐 **Add Authentication** - Implement user login and role-based access
- 📧 **Email Notifications** - Send alerts for order status changes  
- 📊 **Analytics Dashboard** - Add business intelligence and reporting
- 🔄 **Event Streaming** - Implement Apache Kafka for real-time events
- 🧪 **Testing Suite** - Add comprehensive integration tests
- 🌍 **Multi-tenancy** - Support multiple organizations

## 📞 Support & Contributing

- 🐛 **Found a bug?** Open an issue with detailed reproduction steps
- 💡 **Have an idea?** We'd love to hear your suggestions  
- 🤝 **Want to contribute?** Check out our contribution guidelines
- 📚 **Need help?** Join our community discussions

---

<div align="center">

**🌟 Star this repo if you found it helpful!**

*Built with ❤️ for the AI development community*

</div>
