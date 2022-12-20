# Clevertec

1. Используется Java 17, Gradle 7.5.1.
2. В проекте используется Spring, Liquibase, Postgres & Docker.
3. Для билда проекта используйте команду терминала ./gradlew build.

В системе используются следющие эндпоинты:
- Добавление дисконтной карты: POST http://localhost:5869/api/card 
- Создание чека: POST http://localhost:5869/api/receipt
- Добавление продукта: POST http://localhost:5869/api/product
- Удаление продукта: DELETE http://localhost:5869/api/product?productId=1
- Изменение продукта: PUT http://localhost:5869/api/product 

Чеки генерируются в директорию Receipts, автоматически создающуюся в корневой директории проекта при его запуске.
Формат чеков текстовый документ .txt.