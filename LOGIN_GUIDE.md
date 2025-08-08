# Library System Login Guide

## How to Login

This library system uses **HTTP Basic Authentication** for API access. Here are the different ways to login:

## Default Users (Created Automatically)

When you first run the application, these default users are created:

### Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `ROLE_ADMIN`

### Regular User
- **Username**: `user`
- **Password**: `user123`
- **Role**: `ROLE_USER`

## Login Methods

### 1. Using Postman or API Testing Tool

1. **Set the Authorization Header**:
   - Type: `Basic Auth`
   - Username: `admin` (or `user`)
   - Password: `admin123` (or `user123`)

2. **Make API Requests**:
   ```
   GET http://localhost:9090/api/books
   POST http://localhost:9090/api/books
   ```

### 2. Using cURL

```bash
# Get all books (requires authentication)
curl -u admin:admin123 http://localhost:9090/api/books

# Create a new book
curl -X POST -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{"title":"Sample Book","author":"John Doe","isbn":"1234567890"}' \
  http://localhost:9090/api/books
```

### 3. Using Browser (for H2 Database Console)

If using H2 database, you can access the database console:
- **URL**: http://localhost:9090/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## API Endpoints

### Authentication Endpoints
- `POST /api/auth/signup` - Register a new user (no auth required)

### Book Management Endpoints (require authentication)
- `GET /api/books` - Get all books
- `POST /api/books` - Create a new book
- `GET /api/books/{id}` - Get a specific book
- `PUT /api/books/{id}` - Update a book
- `DELETE /api/books/{id}` - Delete a book

## Creating New Users

You can create new users using the signup endpoint:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"username":"newuser","password":"newpass123"}' \
  http://localhost:9090/api/auth/signup
```

## Troubleshooting

### Common Issues:

1. **401 Unauthorized**: 
   - Check username/password
   - Ensure you're using Basic Auth
   - Verify the user exists in the database

2. **403 Forbidden**:
   - User doesn't have required role
   - Check endpoint permissions

3. **Connection Refused**:
   - Make sure the application is running
   - Check if port 9090 is available

### Testing the Application:

1. **Start the application**:
   ```bash
   .\mvnw.cmd spring-boot:run "-Dspring.profiles.active=h2"
   ```

2. **Test login with cURL**:
   ```bash
   curl -u admin:admin123 http://localhost:9090/api/books
   ```

3. **Expected response**: JSON array of books (empty initially)

## Security Notes

- Passwords are encrypted using BCrypt
- All endpoints except `/api/auth/**` require authentication
- HTTP Basic Auth is used for simplicity (not recommended for production)
- For production, consider implementing JWT tokens or session-based auth
