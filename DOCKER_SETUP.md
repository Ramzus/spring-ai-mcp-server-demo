# Merged Docker Compose Setup

This unified Docker Compose configuration runs both the business-app and payment-app together, enabling full integration between the order management system and payment processing.

## Services

### Business App
- **order-backend**: Order management backend (port 8081)
- **order-frontend**: Order management UI (port 5173)

### Payment App  
- **payment-backend**: Payment processing backend (port 8082)
- **payment-frontend**: Payment management UI (port 5174)

### Shared Infrastructure
- **mongo**: MongoDB database (port 27017)

## Usage

### Start All Services
```bash
docker-compose up --build
```

### Start Services in Background
```bash
docker-compose up -d --build
```

### Stop All Services
```bash
docker-compose down
```

### View Logs
```bash
docker-compose logs -f
```

### Access Applications
- Order Management UI: http://localhost:5173
- Payment Management UI: http://localhost:5174
- Order API: http://localhost:8081
- Payment API: http://localhost:8082

## Service Communication

The order backend automatically communicates with the payment backend to retrieve payment status for each order. The applications are configured to use Docker's internal networking:

- Order backend calls payment backend at: `http://payment-backend:8082`
- Frontend applications call their respective backends using container names

## Configuration

### Environment Variables
- **PAYMENT_SERVICE_BASE_URL**: Set to `http://payment-backend:8082` for Docker networking
- **SPRING_PROFILES_ACTIVE**: Set to `docker` for both backends
- **VITE_API_URL**: Frontend API endpoint configuration

### Dependency Order
Services start in the following order:
1. MongoDB
2. Payment Backend
3. Order Backend (depends on payment backend)
4. Both frontends (depend on their respective backends)

## Development Notes

- Payment status is retrieved dynamically when viewing orders
- If payment service is unavailable, orders show "PENDING" status
- Health checks ensure services are ready before marking as healthy
- Volumes are used for MongoDB data persistence

## Testing the Setup

### Automated Integration Test

Run the integration test script to verify all services are working:

**On Linux/macOS/WSL:**
```bash
./test_docker_integration.sh
```

**On Windows:**
```cmd
test_docker_integration.bat
```

The test script will:
- Check Docker availability
- Start all services
- Verify service health
- Test integration between order and payment services
- Display access URLs

### Manual Testing

You can also manually test the services:

```bash
# Check service status
docker compose ps

# View logs
docker compose logs order-backend
docker compose logs payment-backend

# Test API endpoints
curl http://localhost:8081/api/orders
curl http://localhost:8082/actuator/health
```

## Troubleshooting

### Check Service Health
```bash
docker-compose ps
```

### Rebuild Specific Service
```bash
docker-compose up --build <service-name>
```

### View Individual Service Logs
```bash
docker-compose logs <service-name>
```
