#!/bin/bash

# Выполнение запроса для получения объявлений на продажу с 25 комнатами
curl -X POST "http://127.0.0.1:8080/sale/listings/_search" \
    -H "Content-Type: application/json" \
    -d '{
          "rooms": [25]
        }'

