#!/bin/bash

#!/bin/bash

# Bu script ticket-booking dizini içinde çalıştırılmalıdır

echo "Starting user-service..."
cd user-service
mvn spring-boot:run &
USER_PID=$!
cd ..

echo "Starting order-service..."
cd order-service
mvn spring-boot:run &
ORDER_PID=$!
cd ..

echo "Starting payment-service..."
cd payment-service
mvn spring-boot:run &
PAYMENT_PID=$!
cd ..

echo "Starting api-gateway..."
cd api-gateway
mvn spring-boot:run &
GATEWAY_PID=$!
cd ..

echo "All services started!"

# Kullanıcıdan giriş bekleyerek scripti duraklat
read -p "Press Enter to stop all services..."

echo "Stopping services..."
kill $USER_PID $ORDER_PID $PAYMENT_PID $NOTIF_PID $GATEWAY_PID
echo "All services stopped."
