@echo off
REM Docker Compose Integration Test Script for Windows

echo ===================================
echo Docker Compose Integration Test
echo ===================================

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not running. Please start Docker first.
    exit /b 1
)

echo ✅ Docker is running

REM Check if docker-compose is available
docker compose version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ docker compose not found. Please install Docker Compose.
    exit /b 1
)

echo ✅ docker compose is available

REM Start the services
echo.
echo 🚀 Starting all services...
docker compose up -d --build

REM Wait for services to be ready
echo.
echo ⏳ Waiting for services to be ready...
timeout /t 30 /nobreak >nul

REM Check service health
echo.
echo 🔍 Checking service health...

REM Check order backend
curl -f http://localhost:8081/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Order backend is healthy
) else (
    echo ❌ Order backend is not responding
)

REM Check payment backend
curl -f http://localhost:8082/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Payment backend is healthy
) else (
    echo ❌ Payment backend is not responding
)

REM Check order frontend
curl -f http://localhost:5173 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Order frontend is accessible
) else (
    echo ❌ Order frontend is not responding
)

REM Check payment frontend
curl -f http://localhost:5174 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Payment frontend is accessible
) else (
    echo ❌ Payment frontend is not responding
)

REM Test integration
echo.
echo 🔗 Testing service integration...
echo Checking if order service can retrieve orders with payment status...

for /f "delims=" %%i in ('curl -s http://localhost:8081/api/orders') do set ORDER_RESPONSE=%%i
echo %ORDER_RESPONSE% | findstr /C:"paymentStatus" >nul
if %errorlevel% equ 0 (
    echo ✅ Order-Payment integration is working
) else (
    echo ❌ Order-Payment integration failed
    echo Response: %ORDER_RESPONSE%
)

echo.
echo 📊 Service Status Summary:
docker compose ps

echo.
echo 🌐 Access URLs:
echo Order Management UI: http://localhost:5173
echo Payment Management UI: http://localhost:5174
echo Order API: http://localhost:8081
echo Payment API: http://localhost:8082

echo.
echo To stop all services, run: docker compose down
