#!/bin/bash

# Создание объявления о продаже
curl -s -X POST "http://127.0.0.1:8080/sale/listings" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb2NobmV2cjIxQGdtYWlsLmNvbSIsImlhdCI6MTcxNTAzNjI1NCwiZXhwIjoxNzE1MDM3Njk0fQ.hq6GrLpcY8Eocgt6KlR26OyDUEivml0v-gT1Oa8AQ2w" \
    -H "Content-Type: application/json" \
    -d '{
          "city": "Saint Petersburg",
          "street": "Kronverkskiy Prospekt",
          "house": 49,
          "rooms": 25,
          "area": 100.5,
          "price": 15000000,
          "description": "Прекрасный корпус"
        }'


# Отправка типа создателя (в данном случае, владелец)
curl -X POST "http://127.0.0.1:8080/sale/verify" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb2NobmV2cjIxQGdtYWlsLmNvbSIsImlhdCI6MTcxNTAzNjI1NCwiZXhwIjoxNzE1MDM3Njk0fQ.hq6GrLpcY8Eocgt6KlR26OyDUEivml0v-gT1Oa8AQ2w" \
    -H "Content-Type: application/json" \
    -d '{
          "sellerType": "agent"
        }'

# Подтверждение создания объявления
curl -X POST "http://127.0.0.1:8080/sale/confirm" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb2NobmV2cjIxQGdtYWlsLmNvbSIsImlhdCI6MTcxNTAzNjI1NCwiZXhwIjoxNzE1MDM3Njk0fQ.hq6GrLpcY8Eocgt6KlR26OyDUEivml0v-gT1Oa8AQ2w" \
    -H "Content-Type: application/json" \
    -d 'confirm'

