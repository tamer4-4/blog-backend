<div align="center">

# 🚀 Blog Backend API

**A secure, robust RESTful API for a blogging platform built with Java, Spring Boot, and Spring Security.**

[![Java](https://img.shields.io/badge/Java-21%2B-orange?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Security](https://img.shields.io/badge/Security-JWT%20%26%20Spring%20Security-blue?style=flat-square&logo=jsonwebtokens)](https://jwt.io/)
[![Database](https://img.shields.io/badge/Database-MySQL-lightgrey?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)

</div>

---

## 📌 Overview
A production-ready backend application designed to handle blog posts, user comments, and secure authentication following a clean layered architecture. 

### ✨ Key Features
* **Secure Authentication & Authorization:** Implemented using Spring Security and JSON Web Tokens (JWT).
* **DTO & Mapper Pattern:** Utilized Data Transfer Objects (DTOs) to decouple the database entities from API request/response payloads, ensuring secure and clean data transfer using dedicated Mappers.
* **Data Validation:** Robust request payload validation using Jakarta Bean Validation (`@Valid`, `@NotNull`, `@Size`, etc.).
* **Pagination & Sorting:** Efficient data retrieval for posts and comments using Spring Data JPA `Pageable`.
* **Relational Database:** MySQL persistence with proper entity mappings and relationships.

---

## 🔌 API Endpoints Reference

### 🔐 Login & Auth Module
* `POST /api/auth/register` : Register a new user account
* `POST /api/auth/login` : Authenticate user with valid credentials & return JWT token
* `POST /api/auth/logout` : Logout user based on session token

#### Register Example:
**Request (`POST /api/auth/register`):**
```json
{
  "username": "test",
  "email": "test22@gmail.com",
  "password": "123456",
  "role": "ROLE_USER"
}
```
### `Response`:
```Response
 User registered successfully!
```
---

## 🔌 API Endpoints Reference

### Posts Module
* `GET /api/posts` : Retrieve all blog posts
* `POST /api/posts/user` : Create a new post *(Auth Required)*
* `PUT /api/posts/update/{postId}` : Update an existing post *(Auth Required)*
* `DELETE /api/posts/delete/{postId}` : Delete a post *(Auth Required)*

### Comments Module
* `GET /api/comments/post/{postId}` : Get all comments for a specific post
* `POST /api/comments/post/{postId}/user` : Add a comment to a post *(Auth Required)*
* `DELETE /api/comments/{id}` : Delete a comment *(Auth Required)*

---

## ⚙️ Quick start

### 1. Clone the repository:
 ```bash
 git clone [https://github.com/tamer4-4/blog-backend.git](https://github.com/tamer4-4/blog-backend.git)
 cd blog-backe
  ```

### 2. Configure your local database in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/NameDB
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```


