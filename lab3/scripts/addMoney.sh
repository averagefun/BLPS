#!/bin/bash

# Проверка наличия аргумента
if [ $# -eq 0 ]; then
    echo "Usage: $0 <amount>"
    exit 1
fi

# Сумма для пополнения баланса
amount=$1

# Выполнение запроса на пополнение баланса
curl -X POST "http://127.0.0.1:8080/balance/add" \
    -H "Content-Type: application/json" \
    -d $amount

