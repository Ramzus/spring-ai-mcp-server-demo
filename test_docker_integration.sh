#!/bin/bash

# Docker Compose Integration Test Script
echo "==================================="
echo "Docker Compose Integration Test"
echo "==================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

echo "✅ Docker is running"

# Check if docker-compose is available
if ! command -v docker-compose > /dev/null 2>&1; then
    echo "❌ docker-compose not found. Please install docker-compose."
    exit 1
fi

echo "✅ docker-compose is available"

# Start the services
echo ""
echo "🚀 Starting all services..."
docker-compose up -d --build

# Wait for services to be ready
echo ""
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service health
echo ""
echo "🔍 Checking service health..."

# Check order backend
if curl -f http://localhost:8081/actuator/health > /dev/null 2>&1; then
    echo "✅ Order backend is healthy"
else
    echo "❌ Order backend is not responding"
fi

# Check payment backend
if curl -f http://localhost:8082/actuator/health > /dev/null 2>&1; then
    echo "✅ Payment backend is healthy"
else
    echo "❌ Payment backend is not responding"
fi

# Check order frontend
if curl -f http://localhost:5173 > /dev/null 2>&1; then
    echo "✅ Order frontend is accessible"
else
    echo "❌ Order frontend is not responding"
fi

# Check payment frontend
if curl -f http://localhost:5174 > /dev/null 2>&1; then
    echo "✅ Payment frontend is accessible"
else
    echo "❌ Payment frontend is not responding"
fi

# Test integration - check if order service can communicate with payment service
echo ""
echo "🔗 Testing service integration..."
echo "Checking if order service can retrieve orders with payment status..."

ORDER_RESPONSE=$(curl -s http://localhost:8081/api/orders)
if echo "$ORDER_RESPONSE" | grep -q "paymentStatus"; then
    echo "✅ Order-Payment integration is working"
else
    echo "❌ Order-Payment integration failed"
    echo "Response: $ORDER_RESPONSE"
fi

echo ""
echo "📊 Service Status Summary:"
docker-compose ps

echo ""
echo "🌐 Access URLs:"
echo "Order Management UI: http://localhost:5173"
echo "Payment Management UI: http://localhost:5174"
echo "Order API: http://localhost:8081"
echo "Payment API: http://localhost:8082"

echo ""
echo "To stop all services, run: docker-compose down"
