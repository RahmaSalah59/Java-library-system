# Database Connection Setup Guide

## Problem Summary
The main issues with your database connection were:

1. **Placeholder values** in `application.properties` that needed real database credentials
2. **Syntax error** in `pom.xml` (extra newline in MySQL dependency)
3. **Missing H2 database option** for development/testing

## Solutions Implemented

### 1. Fixed MySQL Configuration
Updated `src/main/resources/application.properties` with:
- Proper database URL with connection parameters
- Default MySQL credentials (root with no password)
- Additional JPA properties for better compatibility

### 2. Added H2 Database Support
- Created `application-h2.properties` for in-memory database
- Added H2 dependency to `pom.xml`
- This allows the application to run without MySQL installation

### 3. Fixed POM.xml Syntax
- Removed extra newline in MySQL dependency

## How to Use

### Option 1: Use H2 Database (Recommended for Development)
```bash
# Run with H2 database (no MySQL required)
.\mvnw.cmd spring-boot:run -Dspring.profiles.active=h2
```

### Option 2: Use MySQL Database
1. **Install MySQL** if not already installed
2. **Start MySQL service**
3. **Update credentials** in `application.properties`:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```
4. **Run the application**:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

## Database Access

### H2 Console (when using H2)
- URL: http://localhost:9090/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

### MySQL (when using MySQL)
- Database: `library_system` (will be created automatically)
- Tables: Will be created automatically by JPA

## Troubleshooting

### Common Issues:
1. **MySQL not running**: Start MySQL service
2. **Wrong credentials**: Update username/password in `application.properties`
3. **Port conflicts**: Change `server.port` in `application.properties`
4. **Maven not found**: Use `.\mvnw.cmd` instead of `mvn`

### Error Messages:
- `Connection refused`: MySQL not running or wrong port
- `Access denied`: Wrong username/password
- `Database not found`: Database doesn't exist (should be created automatically)
