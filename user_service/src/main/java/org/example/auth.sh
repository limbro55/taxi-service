#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=========================================="
echo "ДИАГНОСТИКА TAXI-USER-SERVICE"
echo "=========================================="

echo "Запрос: POST $BASE_URL/passengers"
curl -i -X POST "$BASE_URL/passengers" \
     -H "Content-Type: application/json" \
     -d '{"name": "Test", "email": "test'$(date +%s)'@mail.com", "phone": "123456"}'

echo -e "\n------------------------------------------"
echo "Проверка доступности драйверов:"
curl -i -X GET "$BASE_URL/drivers/available"