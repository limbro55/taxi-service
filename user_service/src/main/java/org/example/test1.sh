#!/bin/bash

# Порт твоего Trip Service из логов
BASE_URL="http://localhost:8081/api/v1/trips"

# Актуальный пароль из консоли IDEA (проверь его!)
USER_PASSWORD="fc04c948-5e47-4c84-80b8-37c407429fbf"
# Мы будем использовать "123" как логин, чтобы Long.parseLong в коде не упал
USER_AUTH="123:$USER_PASSWORD"

echo "=========================================="
echo "ТЕСТИРОВАНИЕ TRIP-SERVICE"
echo "=========================================="

# 1. Создание поездки
echo "Шаг 1: Создание поездки..."
# Используем поля 'origin' и 'destination', как в твоем TripRequest.java
R_JSON='{"origin": "Центр", "destination": "Аэропорт"}'

R_RES=$(curl -s -u "$USER_AUTH" -X POST "$BASE_URL" \
     -H "Content-Type: application/json" \
     -d "$R_JSON")

echo "Ответ сервера: $R_RES"

# 2. Проверка истории
echo -e "\nШаг 2: Проверка истории для пассажира 123..."
HIST_RES=$(curl -s -u "$USER_AUTH" -X GET "$BASE_URL?passengerId=123")
echo "История: $HIST_RES"

echo "=========================================="