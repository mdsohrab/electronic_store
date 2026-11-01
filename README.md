# 🛒 Electronic Store – REST API

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)

Backend for an online electronics store, built with **Spring Boot 3**, **Spring Data JPA** and **MySQL**.
It manages **users**, **categories** and **products**, with image upload, search, pagination and sorting.

## ✨ Features

- **Users** – CRUD, search by keyword, lookup by email, profile image upload/download
- **Categories** – CRUD, cover image upload, create or assign products inside a category
- **Products** – CRUD, list only *live* products, search by title, product image upload
- **Pagination & sorting** on every list endpoint (`pageNumber`, `pageSize`, `sortBy`, `sortDir`)
- **Validation** with Bean Validation plus a custom `@ImageNameValid` annotation
- **Global exception handling** returning a consistent `ApiResponseMessage`
- **DTO mapping** with ModelMapper, so entities never leak out of the API

## 🧱 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 (Web, Data JPA, Validation) |
| Database | MySQL 8 + Hibernate |
| Utilities | Lombok, ModelMapper |
| Build | Maven |

## 📁 Project Structure

```
src/main/java/Electronic/Store/Electronic/Store/
├── Controller/    REST controllers (User, Category, Product)
├── services/      Service interfaces + impl/ (business logic, file storage)
├── Repositories/  Spring Data JPA repositories
├── Entities/      JPA entities (UserEntity, CategoryEntity, ProductEntity)
├── Dtos/          Request/response DTOs, PageableResponse
├── Exceptions/    GlobalExceptionsHandler and custom exceptions
├── Validate/      Custom image-name validator
├── Config/        ModelMapper bean
└── helper/        Pagination helper
```

## 🔌 API Overview

Base URL: `http://localhost:9090`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/users` | Create a user |
| `PUT` | `/users/{userId}` | Update a user |
| `DELETE` | `/users/{userId}` | Delete a user |
| `GET` | `/users` | List users (paginated) |
| `GET` | `/users/{userId}` | Get a user by id |
| `GET` | `/users/email/{email}` | Get a user by email |
| `GET` | `/users/search/{keyword}` | Search users |
| `POST` / `GET` | `/users/image/{userId}` | Upload / serve profile image |
| `POST` | `/categories` | Create a category |
| `PUT` / `DELETE` | `/categories/{categoryId}` | Update / delete a category |
| `GET` | `/categories` | List categories (paginated) |
| `GET` | `/categories/{categoryId}` | Get a category |
| `POST` / `GET` | `/categories/image/{categoryId}` | Upload / serve cover image |
| `POST` | `/categories/{categoryId}/products` | Create a product inside a category |
| `PUT` | `/categories/{categoryId}/products/{productId}` | Assign a product to a category |
| `GET` | `/categories/{categoryId}/products` | Products in a category |
| `POST` | `/products` | Create a product |
| `PUT` / `DELETE` | `/products/{productId}` | Update / delete a product |
| `GET` | `/products/productId/{productId}` | Get a product |
| `GET` | `/products` | List products (paginated) |
| `GET` | `/products/live` | List live products only |
| `GET` | `/products/search/{query}` | Search products by title |
| `POST` / `GET` | `/products/image/{productId}` | Upload / serve product image |

Example – page 2 of products, newest price first:

```bash
curl "http://localhost:9090/products?pageNumber=1&pageSize=5&sortBy=price&sortDir=desc"
```

## 🚀 Getting Started

**Requirements:** Java 17+, Maven 3.9+, MySQL 8

1. Create the database:
   ```sql
   CREATE DATABASE electronic_store;
   ```
2. Set your database credentials as environment variables (nothing secret lives in the repo):
   ```bash
   export MYSQL_HOST=localhost
   export MYSQL_USER=root
   export MYSQL_PASSWORD=your_password
   ```
3. Run the app:
   ```bash
   git clone https://github.com/mdsohrab/electronic_store.git
   cd electronic_store
   mvn spring-boot:run
   ```

The API starts on **port 9090**. Tables are created automatically (`ddl-auto=update`).
Uploaded images are stored under `images/users`, `images/category` and `images/product`.

### Configuration

| Variable | Default | Purpose |
|---|---|---|
| `MYSQL_HOST` | `localhost` | Database host |
| `MYSQL_PORT` | `3306` | Database port |
| `MYSQL_DB` | `electronic_store` | Database name |
| `MYSQL_USER` | `root` | Database user |
| `MYSQL_PASSWORD` | *(empty)* | Database password |

## 🗺️ Roadmap

- [ ] JWT authentication with Spring Security
- [ ] Cart and order modules
- [ ] Swagger / OpenAPI documentation
- [ ] Docker Compose setup with MySQL

## 👤 Author

**Md Sohrab** – [GitHub](https://github.com/mdsohrab)

## 📄 License

Released under the [MIT License](LICENSE).
