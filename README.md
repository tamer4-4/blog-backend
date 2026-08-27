\# Blog Backend API



A secure and robust RESTful API for a blogging platform, built with \*\*Java\*\*, \*\*Spring Boot\*\*, and \*\*Spring Security (JWT)\*\*, featuring complete CRUD operations and clean architectural layers.



\---



\## Tech Stack

\* \*\*Language \& Framework:\*\* Java 21+, Spring Boot, Spring Data JPA  ,validation ,lombok

\* \*\*Security:\*\* Spring Security, JSON Web Token (JWT)

\* \*\*Database:\*\* MySQL

\* \*\*Documentation:\*\* SpringDoc OpenAPI (Swagger UI)

\* \*\*Build Tool \& Container:\*\* Maven, Docker



\---



&#x20;Architecture \& Project Structure

The project follows a standard layered architecture to maintain separation of concerns:



\# Project Structure

&#x20;  src/main/java/com/blog/blogWeb/

&#x20;   ├── controller/    # REST Endpoints (Handling HTTP requests)

&#x20;   ├── service/       # Business logic layer

&#x20;   ├── repository/    # Data access layer (Spring Data JPA)

&#x20;   ├── entity/        # Database models (User, Post, Comment, Role)

&#x20;   ├── dto/           # Data Transfer Objects for request/response mapping

&#x20;   ├── security/      # JWT filters, security config, and user details service

&#x20;   └── exception/     # Global exception handling



🔒 Security & Authentication (JWT)
The application uses Spring Security combined with JWT (JSON Web Tokens) for stateless authentication:

Authentication Filter: When a user logs in via /api/auth/login, credentials are validated, and a signed JWT token is generated and returned.

Authorization Filter: For any subsequent protected request, the client must send the token in the HTTP header:
Authorization: Bearer <YOUR_JWT_TOKEN>

Security Config: Public endpoints (like registration and login) are open, while management and posting endpoints require a valid authenticated token.

🔗 API Endpoints Overview
1. Authentication (/api/auth)
POST /register - Register a new user account.

POST /login - Authenticate user and receive a JWT token.

2. Posts (/api/posts)
GET / - Retrieve all blog posts.

POST /user - Create a new post (Requires Authentication).

PUT /update/{postId} - Update an existing post (Requires Authentication).

DELETE /delete/{postId} - Delete a post (Requires Authentication).

3. Comments (/api/comments)
GET /post/{postId} - Get all comments for a specific post.

POST /post/{postId}/user - Add a comment to a post (Requires Authentication).

DELETE /{id} - Delete a comment (Requires Authentication).

PUT /update/post/{id} - Update an existing comment (Requires Authentication).

 How to Build and Run Locally
1. Clone the Repository
Bash
git clone [https://github.com/tamer4-4/blog-backend.git](https://github.com/tamer4-4/blog-backend.git)
cd blog-backend

2. Configure Database
Update your src/main/resources/application.properties with your local MySQL credentials:

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/blogWeb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

3. Build and Run Using Maven
Bash
mvn clean install

mvn spring-boot:run

4. Explore via Swagger UI
Once running, open your browser and test the endpoints interactively at:
👉 http://localhost:8080/swagger-ui/index.html