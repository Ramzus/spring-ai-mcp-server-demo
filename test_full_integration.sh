#!/bin/bash

# Script pour tester l'intégration Docker complète
# Usage: ./test_full_integration.sh

set -e

echo "🚀 Démarrage du test d'intégration complète..."

# Couleurs pour l'affichage
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Nettoyage initial
print_status "Nettoyage des conteneurs existants..."
docker-compose down --volumes --remove-orphans || true

# Construction et démarrage
print_status "Construction et démarrage des services..."
docker-compose up --build -d

# Attendre que tous les services soient prêts
print_status "Attente du démarrage des services..."
sleep 30

# Test des health checks
print_status "Vérification des health checks..."

# Fonction pour tester un endpoint
test_endpoint() {
    local service=$1
    local url=$2
    local max_attempts=10
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        print_status "Test $service (tentative $attempt/$max_attempts)..."
        if curl -f -s "$url" > /dev/null 2>&1; then
            print_success "$service est accessible à $url"
            return 0
        fi
        sleep 10
        ((attempt++))
    done
    
    print_error "$service n'est pas accessible à $url après $max_attempts tentatives"
    return 1
}

# Tests des services backend
test_endpoint "Order Backend" "http://localhost:8081/actuator/health"
test_endpoint "Payment Backend" "http://localhost:8082/actuator/health"
test_endpoint "Incident Backend" "http://localhost:8083/api/incidents/health"

# Tests des services frontend
test_endpoint "Order Frontend" "http://localhost:5173"
test_endpoint "Payment Frontend" "http://localhost:5174"
test_endpoint "Incident Frontend" "http://localhost:5175"

# Test du MCP Server
test_endpoint "MCP Server" "http://localhost:8085/health"

# Test de MongoDB
print_status "Test de MongoDB..."
if docker exec mcp-mongodb mongosh --eval "db.runCommand({ hello: 1 })" > /dev/null 2>&1; then
    print_success "MongoDB est accessible"
else
    print_error "MongoDB n'est pas accessible"
fi

# Affichage de l'état des conteneurs
print_status "État des conteneurs:"
docker-compose ps

# Résumé des services
echo
print_success "🎉 Test d'intégration terminé!"
echo
print_status "Services accessibles:"
echo "  📋 Order App: http://localhost:5173"
echo "  💰 Payment App: http://localhost:5174"
echo "  🚨 Incident App: http://localhost:5175"
echo "  🔧 MCP Server: http://localhost:8085"
echo "  🗄️  MongoDB: localhost:27017"
echo
print_status "APIs Backend:"
echo "  📋 Order API: http://localhost:8081"
echo "  💰 Payment API: http://localhost:8082"
echo "  🚨 Incident API: http://localhost:8083"
echo
print_status "Health Checks:"
echo "  📋 Order: http://localhost:8081/actuator/health"
echo "  💰 Payment: http://localhost:8082/actuator/health"
echo "  🚨 Incident: http://localhost:8083/actuator/health"

echo
print_warning "Pour arrêter tous les services: docker-compose down"
print_warning "Pour voir les logs: docker-compose logs -f [service-name]"
