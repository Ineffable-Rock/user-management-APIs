# User Management System (Secure REST API)

A production-grade Backend REST API built with Java & Spring Boot. It features role-based security, relational database management, and automated testing.

## 🚀 Tech Stack
* **Core:** Java 21, Spring Boot 3.x
* **Database:** PostgreSQL, Hibernate (JPA)
* **Security:** Spring Security, BCrypt Encryption, Role-Based Access Control (RBAC)
* **Testing:** JUnit 5, Mockito, MockMvc
* **Build Tool:** Maven

## ⚙️ Key Features
* **CRUD Operations:** Full management of Users and related Posts.
* **Relational Data:** Implemented One-to-Many relationships (User → Posts).
* **Advanced Data Handling:** Pagination, Sorting, and DTO Pattern for data isolation.
* **Robust Security:**
    * Authentication via Basic Auth.
    * BCrypt Password Hashing.
    * Authorization logic (Admin vs. User roles).
* **Input Validation:** `@Valid` constraints to ensure data integrity.
* **Error Handling:** Global Exception Handler for clean JSON error responses.

## 🛠️ Installation & Run

**Prerequisites:**
* Java 21+ installed
* PostgreSQL running locally

1. Clone the repository

git clone [https://github.com/yourusername/backend-app.git](https://github.com/yourusername/backend-app.git)

2. Configure Database Update src/main/resources/application.properties:

Properties

spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

3. Build the JAR

Bash
./mvnw clean package -DskipTests

4. Run the Application

Bash
java -jar target/Backend-0.0.1-SNAPSHOT.jar

🧪 API Endpoints
1. Method:- POST
   Endpoint:- /users	
   Description:- Register a new user	
   Auth Required:- ❌ No

2. Method:- GET	
   Endpoint:- /users
   Description:- Get all users
   Auth Required:- ✅ Yes

3. Method:- POST	
   Endpoint:- /users
   Description:- Create a post
   Auth Required:- ✅ Yes
4. Method:- GET
   Endpoint:- /posts
   Description:- Get paginated posts
   Auth Required:- 	✅ Yes