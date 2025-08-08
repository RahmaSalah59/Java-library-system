
# Library Management System

A simple Java Spring Boot application for managing books with user authentication using JWT tokens.

## Features

* User Signup with username and password
* User Login with JWT token generation
* Add, update, delete, and list books (only by authenticated users)
* Secure API endpoints using JWT authentication

## Technologies Used

* Java 17
* Spring Boot 3
* Spring Data JPA
* MySQL database
* Maven build tool

## Getting Started

### Prerequisites

* Java 17 or higher installed
* MySQL server running
* Maven installed

### Setup

1. Clone the repository:

   ```bash
   git clone <[your-repo-url](https://github.com/RahmaSalah59/Java-library-system)>
   ```

2. Configure MySQL connection in `src/main/resources/application.properties`:

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### API Usage

* **Signup:**
  `POST /api/auth/signup`
  Body JSON:

  ```json
  {
    "username": "your_username",
    "password": "your_password"
  }
  ```

* **Book APIs:**

  * `GET /api/books` - list your books
  * `POST /api/books` - add new book
  * `PUT /api/books/{id}` - update book by id
  * `DELETE /api/books/{id}` - delete book by id

---

