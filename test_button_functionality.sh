#!/bin/bash

echo "=== Test de la fonctionnalité du bouton 'Faire avancer' ==="
echo ""

echo "1. État initial des commandes :"
curl -s http://localhost:8081/orders | jq '.[] | {orderId, customerName, status, paymentStatus}'

echo ""
echo "2. Test d'avancement de la commande 102 (DELIVERED -> FINISHED) :"
curl -X POST http://localhost:8081/orders/102/next -H "Content-Type: application/json" -s | jq '{orderId, status}'

echo ""
echo "3. Test d'avancement de la commande 103 (SHIPPED -> DELIVERED) :"
curl -X POST http://localhost:8081/orders/103/next -H "Content-Type: application/json" -s | jq '{orderId, status}'

echo ""
echo "4. État final des commandes :"
curl -s http://localhost:8081/orders | jq '.[] | {orderId, customerName, status, paymentStatus}'

echo ""
echo "=== Test terminé ==="
