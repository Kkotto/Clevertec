# Clevertec

[![wakatime](https://wakatime.com/badge/user/f7dbe84c-6f3c-42e2-b6ac-fcc958d0eabb/project/0ac0a675-86f8-473a-a104-735539a6050c.svg)](https://wakatime.com/badge/user/f7dbe84c-6f3c-42e2-b6ac-fcc958d0eabb/project/0ac0a675-86f8-473a-a104-735539a6050c)

Полное задание | Full task description (only russian version is provided): [Тестовое задание Backend Dev Course.pdf](https://github.com/Kkotto/Clevertec/files/10550963/Backend.Dev.Course.pdf)

# Table of contents
- [Russian](#Russian)
  - [Эндпоинты](#Эндпоинты)
  - [Работа с файлами](#Работа-с-файлами)
- [English](#English)
  - [Endpoints](#Endpoints)
  - [Working with files](#Working-with-files)

# Russian

1. Используется Java 17, Gradle 7.5.1.
2. Используется Spring, Liquibase, Postgres & Docker.
3. Для билда проекта используйте команду терминала ./gradlew build.

## Эндпоинты

В системе используются следющие эндпоинты:
- Добавление дисконтной карты: POST http://localhost:5869/api/card 
- Создание чека: POST http://localhost:5869/api/receipt
- Добавление продукта: POST http://localhost:5869/api/product
- Удаление продукта: DELETE http://localhost:5869/api/product?productId=
- Изменение продукта: PUT http://localhost:5869/api/product?productId=
- Запись всех продуктов в файл: GET http://localhost:5869/api/get-product-list
- Чтение продуктов из файла и сохранение в БД: POST http://localhost:5869/api/read-product-list

## Работа с файлами

### Генерация чека

- Чеки генерируются в директорию Receipts.
- Директория Receipts автоматически создается в корневой директории проекта.
- Формат чеков - текстовый документ .txt.
- Полное имя файла в формате "Receipt dd-MM-yyyy HH-mm-ss.txt".

### Запись продуктов в файл

- Файл генерируется в директорию Products.
- Директория Products создается автоматически в корневой директории проекта.
- Формат файла - .csv. 
- Полное имя файла в формате "Products-dd-MM-yyyy HH-mm-ss.csv".

### Чтение продуктов из файла

- Формат читаемого файла - .csv.
- Файл отправляется в теле запроса (body - form-data - key: file, value: файл в формате .csv)
- Валидные записи формата "id;name;price;createAt" сохраняются в БД.
- Готовый к чтению файл: [Products-22-12-2022 01-16-55.csv](https://github.com/Kkotto/Clevertec/files/10282370/Products-22-12-2022.01-16-55.csv)

# English

1. Versioning: Java 17, Gradle 7.5.1.
2. Stack: Spring, Liquibase, Postgres & Docker.
3. To build the project, use ./gradlew build.

## Endpoints

Project endpoints:
- Add discount card: POST http://localhost:5869/api/card 
- Generate receipt: POST http://localhost:5869/api/receipt
- Add product: POST http://localhost:5869/api/product
- Delete product: DELETE http://localhost:5869/api/product?productId=
- Update product: PUT http://localhost:5869/api/product?productId=
- Write all products to file: GET http://localhost:5869/api/get-product-list
- Read all products from file (with saving products to the databse): POST http://localhost:5869/api/read-product-list

## Working with files

### Receipt generation

- All receipts are saved to the Receipts directory.
- The Receipt directory is automatically created at the root of the project.
- Receipts are generated in a .txt file.
- The file name for receipts follows the format: "Receipt dd-MM-yyyy HH-mm-ss.txt".

### Writing Products to the file

- The file is generated to the Products directory.
- The Products directory is automatically created at the root of the project.
- The file format is .csv. 
- The file name for products follows the format: "Products-dd-MM-yyyy HH-mm-ss.csv".

### Reading Products from the file

- The file format is .csv.
- Send the file in the request body (body - form-data - key: file, value: файл в формате .csv)
- Valid records in the format of "id;name;price;createAt" are saved to the database.
- Sample file (ready for usa): [Products-22-12-2022 01-16-55.csv](https://github.com/Kkotto/Clevertec/files/10282370/Products-22-12-2022.01-16-55.csv)
