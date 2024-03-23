#!/bin/bash

# Создание объявления о продаже
create_response=$(curl -s -X POST "http://127.0.0.1:8080/sale/listings" \
    -H "Content-Type: application/json" \
    -d '{
          "city": "Saint Petersburg",
          "street": "Kronverkskiy Prospekt",
          "house": 49,
          "rooms": 25,
          "area": 100.5,
          "price": 15000000,
          "description": "Прекрасный корпус"
        }')


# Отправка типа создателя (в данном случае, владелец)
curl -X POST "http://127.0.0.1:8080/sale/verify" \
    -H "Content-Type: application/json" \
    -d '{
          "sellerType": "owner"
        }'

# Подтверждение создания объявления
curl -X POST "http://127.0.0.1:8080/sale/confirm" \
    -H "Content-Type: application/json" \
    -d 'confirm'

