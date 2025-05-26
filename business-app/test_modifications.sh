#!/bin/bash

# Test script for order modifications
echo "=== Order Object Modification Test ==="
echo "This script tests the implemented changes:"
echo "1. Removed photoUrl field"
echo "2. Added numberOfItems field" 
echo "3. Added payment status integration"
echo ""

echo "Step 1: Starting Payment Backend (port 8082)..."
cd /mnt/d/Devs/sources/agentic-ai-demo/payment-app/back
./gradlew bootRun &
PAYMENT_PID=$!
echo "Payment backend PID: $PAYMENT_PID"

echo "Waiting for payment backend to start..."
sleep 30

echo ""
echo "Step 2: Starting Order Backend (port 8081)..."
cd /mnt/d/Devs/sources/agentic-ai-demo/business-app/back
./gradlew bootRun &
ORDER_PID=$!
echo "Order backend PID: $ORDER_PID"

echo "Waiting for order backend to start..."
sleep 30

echo ""
echo "Step 3: Testing API endpoints..."

echo "Testing order endpoint:"
curl -s "http://localhost:8081/orders" | jq '.' || echo "Failed to get orders or jq not available"

echo ""
echo "Step 4: Starting Frontend (port 5173)..."
cd /mnt/d/Devs/sources/agentic-ai-demo/business-app/front
npm run dev &
FRONTEND_PID=$!
echo "Frontend PID: $FRONTEND_PID"

echo ""
echo "Test complete! Check the following:"
echo "1. Backend logs for successful startup"
echo "2. API response includes numberOfItems and paymentStatus fields"
echo "3. Frontend displays order cards with item count and payment status"
echo "4. No photo/photoUrl fields in the response"
echo ""
echo "Access frontend at: http://localhost:5173"
echo ""
echo "To stop services:"
echo "kill $PAYMENT_PID $ORDER_PID $FRONTEND_PID"
