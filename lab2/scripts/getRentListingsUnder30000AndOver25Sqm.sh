#!/bin/bash

# Выполнение запроса для получения объявлений на аренду дешевле 30000 и больше 25 метров
curl -X POST "http://127.0.0.1:8080/rent/listings/_search" \
    -H "Content-Type: application/json" \
    -d '{
          "maxPrice": 30000,
          "minArea": 25
        }'

