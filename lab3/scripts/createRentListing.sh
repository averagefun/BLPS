#!/bin/bash

# Создание объявления о продаже
create_response=$(curl -s -X POST "http://127.0.0.1:8080/rent/listings" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb2NobmV2cjIxQGdtYWlsLmNvbSIsImlhdCI6MTcxNTAzNjI1NCwiZXhwIjoxNzE1MDM3Njk0fQ.hq6GrLpcY8Eocgt6KlR26OyDUEivml0v-gT1Oa8AQ2w" \
    -H "Content-Type: application/json" \
    -d '{
          "city": "Saint Petersburg",
          "street": "Kronverkskiy Prospekt",
          "house": 49,
          "rooms": 3,
          "area": 100.5,
          "price": 15000,
	  "minDuration": 12,
          "description": "Прекрасный корпус"
        }')


# Отправка типа создателя (в данном случае, агент)
curl -X POST "http://127.0.0.1:8080/rent/verify" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb2NobmV2cjIxQGdtYWlsLmNvbSIsImlhdCI6MTcxNTAzNjI1NCwiZXhwIjoxNzE1MDM3Njk0fQ.hq6GrLpcY8Eocgt6KlR26OyDUEivml0v-gT1Oa8AQ2w" \
    -H "Content-Type: application/json" \
    -d '{
          "sellerType": "agent"
        }'

# Подтверждение создания объявления
curl -X POST "http://127.0.0.1:8080/rent/confirm" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb2NobmV2cjIxQGdtYWlsLmNvbSIsImlhdCI6MTcxNTAzNjI1NCwiZXhwIjoxNzE1MDM3Njk0fQ.hq6GrLpcY8Eocgt6KlR26OyDUEivml0v-gT1Oa8AQ2w" \
    -H "Content-Type: application/json" \
    -d 'confirm'


