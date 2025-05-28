#!/bin/bash

# Test script to verify order status progression logic with payment integration
echo "=== Testing Order Status Progression Logic ==="

ORDER_API="http://localhost:8081"
PAYMENT_API="http://localhost:8082"

# Function to test order progression
test_order_progression() {
    local order_id=$1
    local expected_behavior=$2
    
    echo "Testing order $order_id - $expected_behavior"
    
    # Get current order status
    echo "Current order status:"
    curl -s "$ORDER_API/orders/$order_id" | jq '.status, .paymentStatus'
    
    # Try to advance order
    echo "Attempting to advance order..."
    response=$(curl -s -w "%{http_code}" -X POST "$ORDER_API/orders/$order_id/next-step")
    http_code="${response: -3}"
    
    if [ "$http_code" -eq 200 ]; then
        echo "✅ Order advanced successfully"
        echo "New status:"
        curl -s "$ORDER_API/orders/$order_id" | jq '.status'
    else
        echo "❌ Order advancement failed with HTTP $http_code"
        echo "Response: ${response%???}"
    fi
    
    echo "---"
}

# Wait for services to be ready
echo "Waiting for services to start..."
sleep 10

# Test cases
echo "1. Testing order with COMPLETED payment (should allow progression)"
test_order_progression 100 "COMPLETED payment - should allow progression"

echo "2. Testing order with PENDING payment (should block progression beyond PENDING)"
test_order_progression 102 "PENDING payment - should block progression"

echo "3. Testing order with FAILED payment (should block progression beyond PENDING)"
test_order_progression 103 "FAILED payment - should block progression"

echo "4. Testing order with no payment data (should block progression)"
test_order_progression 104 "No payment - should block progression"

echo "=== Test Complete ==="
