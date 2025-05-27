# Démo AI Agentique - Intégration Docker Complète

Cette démo présente une architecture de microservices complète avec un serveur MCP (Model Context Protocol) pour l'IA agentique.

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

## 🚀 Services Inclus

### Applications Frontend
- **Order App**: Interface de gestion des commandes (Vue.js) - Port 5173
- **Payment App**: Interface de gestion des paiements (Vue.js) - Port 5174
- **Incident App**: Interface de gestion des incidents (Vue.js) - Port 5175

### APIs Backend
- **Order Backend**: API REST pour les commandes (Spring Boot) - Port 8081
- **Payment Backend**: API REST pour les paiements (Spring Boot) - Port 8082
- **Incident Backend**: API REST pour les incidents (Spring Boot) - Port 8083

### Infrastructure
- **MCP Server**: Serveur de protocole de contexte de modèle (Spring Boot) - Port 8085
- **MongoDB**: Base de données pour le serveur MCP - Port 27017

## 🔧 Configuration

### Certificats ADEO
Tous les services Java incluent automatiquement le certificat racine ADEO pour les connexions HTTPS sécurisées.

### Base de Données
- **Order Backend**: Base de données H2 intégrée avec données d'exemple
- **Payment Backend**: Base de données H2 intégrée avec données d'exemple
- **Incident Backend**: Base de données H2 intégrée avec données d'exemple
- **MCP Server**: MongoDB partagé pour les métadonnées

### Variables d'Environnement
```yaml
# URLs des services backend
ORDER_SERVICE_BASE_URL=http://order-backend:8081
PAYMENT_SERVICE_BASE_URL=http://payment-backend:8082
INCIDENT_SERVICE_BASE_URL=http://incident-backend:8083

# MongoDB pour MCP Server
MONGODB_URI=mongodb://mongo:27017/mcp-demo

# URLs des APIs pour les frontends
VITE_API_URL=http://[service]-backend:[port]
```

## 🚀 Démarrage Rapide

### Prérequis
- Docker et Docker Compose installés
- Ports 5173-5175, 8081-8083, 8085, et 27017 disponibles

### Démarrage
```bash
# Cloner et naviguer vers le projet
cd /path/to/agentic-ai-demo

# Construire et démarrer tous les services
docker-compose up --build -d

# Vérifier l'état des services
docker-compose ps

# Voir les logs
docker-compose logs -f
```

### Test d'Intégration
```bash
# Exécuter le script de test complet
./test_full_integration.sh
```

## 🌐 Accès aux Applications

### Interfaces Utilisateur
- **Gestion des Commandes**: http://localhost:5173
- **Gestion des Paiements**: http://localhost:5174
- **Gestion des Incidents**: http://localhost:5175

### APIs REST
- **Orders API**: http://localhost:8081
  - Health Check: http://localhost:8081/actuator/health
  - Swagger UI: http://localhost:8081/swagger-ui.html
- **Payments API**: http://localhost:8082
  - Health Check: http://localhost:8082/actuator/health
- **Incidents API**: http://localhost:8083
  - Health Check: http://localhost:8083/actuator/health

### MCP Server
- **Endpoint**: http://localhost:8085
- **Health Check**: http://localhost:8085/health

### Base de Données
- **MongoDB**: localhost:27017
  - Base: mcp-demo

## 🔍 Monitoring et Debug

### Health Checks
Tous les services incluent des health checks automatiques:
- Vérification toutes les 30 secondes
- 3 tentatives avant de marquer comme défaillant
- Timeout de 10 secondes

### Logs
```bash
# Tous les services
docker-compose logs -f

# Service spécifique
docker-compose logs -f order-backend
docker-compose logs -f mcp-server
```

### Debug des Conteneurs
```bash
# Se connecter à un conteneur
docker exec -it order-backend bash
docker exec -it mcp-mongodb mongosh

# Vérifier l'état du réseau
docker network ls
docker network inspect agentic-ai-demo_app-network
```

## 🛠️ Développement

### Modification et Redémarrage
```bash
# Reconstruire un service spécifique
docker-compose up --build -d order-backend

# Redémarrer sans reconstruction
docker-compose restart order-backend
```

### Nettoyage
```bash
# Arrêter tous les services
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down --volumes

# Nettoyage complet (images, conteneurs, volumes)
docker-compose down --volumes --rmi all
```

## 📊 Données d'Exemple

### Orders
- 3 commandes d'exemple avec statuts variés
- Photos d'exemple via picsum.photos
- Montants et dates réalistes

### Payments
- Paiements liés aux commandes
- Différents statuts (PENDING, COMPLETED, FAILED)

### Incidents
- Incident d'exemple lié aux commandes
- Workflow de résolution avec assignation

## 🔐 Sécurité

- Certificats ADEO intégrés dans tous les conteneurs Java
- Communications inter-services via réseau Docker privé
- Health checks pour la supervision
- Variables d'environnement pour la configuration

## 🚨 Résolution des Problèmes

### Services qui ne démarrent pas
1. Vérifier les ports disponibles: `netstat -an | grep LISTEN`
2. Vérifier les logs: `docker-compose logs [service-name]`
3. Vérifier l'espace disque: `df -h`

### Problèmes de certificats
1. Vérifier la connectivité: `curl -I https://igc.groupeadeo.com/ADEO_ROOT_CA1.crt`
2. Reconstruire les images: `docker-compose build --no-cache`

### Problèmes de base de données
1. Vérifier MongoDB: `docker exec mcp-mongodb mongosh --eval "db.runCommand({ hello: 1 })"`
2. Vérifier les volumes: `docker volume ls`

## 📝 Notes de Version

- **v1.0**: Configuration initiale avec Order, Payment et Incident apps
- **v1.1**: Intégration MCP Server et MongoDB
- **v1.2**: Ajout des certificats ADEO et health checks
- **v1.3**: Logging complet et données d'exemple
