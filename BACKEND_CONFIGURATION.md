# Backend Configuration - CORS & API Setup

## Overview

The backend has been configured to work seamlessly with the frontend React application. CORS (Cross-Origin Resource Sharing) has been enabled to allow the frontend running on `localhost:5173` to communicate with the backend on `localhost:8080`.

---

## 🔧 WebConfig.java - CORS Configuration

**File**: `src/main/java/com/abiodunelijah/config/WebConfig.java`

### Purpose

The `WebConfig` class configures CORS settings to allow the frontend to make HTTP requests to the backend API without being blocked by browser security policies.

### Configuration Details

#### Allowed Origins
```
- http://localhost:5173 (Vite dev server)
- http://localhost:3000 (Alternative port)
- http://127.0.0.1:5173 (Localhost IP)
- http://127.0.0.1:3000 (Localhost IP alternative)
```

#### Allowed HTTP Methods
- GET
- POST
- PUT
- DELETE
- OPTIONS (for preflight requests)
- PATCH

#### Allowed Headers
- Content-Type
- Authorization
- X-Requested-With
- Accept
- Origin

#### Credentials
- `allowCredentials(true)` - Allows cookies and authentication headers

#### Cache Duration
- `maxAge(3600)` - Caches preflight responses for 1 hour (3600 seconds)

### Code Example

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", ...)
                .allowedMethods("GET", "POST", "PUT", "DELETE", ...)
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

---

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. Get All Contacts
```
GET /api/contacts
```

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phone": "(555) 123-4567",
    "address": "123 Main St"
  }
]
```

#### 2. Get Contact by ID
```
GET /api/contacts/{id}
```

**Example**: `GET /api/contacts/1`

**Response** (200 OK):
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St"
}
```

#### 3. Create Contact
```
POST /api/contacts
Content-Type: application/json
```

**Request Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St"
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St"
}
```

#### 4. Update Contact
```
PUT /api/contacts/{id}
Content-Type: application/json
```

**Example**: `PUT /api/contacts/1`

**Request Body**:
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "phone": "(555) 987-6543",
  "address": "456 Oak Ave"
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Doe",
  "phone": "(555) 987-6543",
  "address": "456 Oak Ave"
}
```

#### 5. Delete Contact
```
DELETE /api/contacts/{id}
```

**Example**: `DELETE /api/contacts/1`

**Response** (200 OK):
```json
"Contact deleted successfully"
```

#### 6. Search Contacts
```
GET /api/contacts/search?firstName=john&lastName=Doe&phoneNumber=555
```

**Parameters**:
- `firstName` (optional) - Search by first name
- `lastName` (optional) - Search by last name
- `phoneNumber` (optional) - Search by phone number

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phone": "(555) 123-4567",
    "address": "123 Main St"
  }
]
```

---

## 🔄 Backward Compatibility

The API maintains backward compatibility with the old endpoints:

| Old Endpoint | New Endpoint | Method |
|--------------|--------------|--------|
| `/api/v1/contacts/add-contact` | `/api/contacts` | POST |
| `/api/v1/contacts/all-contacts` | `/api/contacts` | GET |
| `/api/v1/contacts/get-contact/{id}` | `/api/contacts/{id}` | GET |
| `/api/v1/contacts/update-contact/{id}` | `/api/contacts/{id}` | PUT |
| `/api/v1/contacts/delete-contact/{id}` | `/api/contacts/{id}` | DELETE |

Old endpoints still work but are marked as `@deprecated`. Use new endpoints for new development.

---

## 🚀 Running the Backend

### Prerequisites
- Java 25+ (as specified in pom.xml)
- Maven
- PostgreSQL running on localhost:5432
- Database: `phonebook`
- User: `coder2client`
- Password: `pastoral2u`

### Start Backend

```bash
cd backend
./mvnw spring-boot:run
```

or

```bash
./mvnw.cmd spring-boot:run  # Windows
```

### Expected Output
```
Started BackendApplication in X seconds
```

### API Documentation

Once started, access Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

API Docs JSON:
```
http://localhost:8080/api-docs
```

---

## 📋 Application Properties

**File**: `src/main/resources/application.properties`

### Database Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook
spring.datasource.username=coder2client
spring.datasource.password=pastoral2u
spring.datasource.driver-class-name=org.postgresql.Driver
```

### JPA/Hibernate Configuration
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### OpenAPI Documentation
```properties
springdoc.api-docs.path=/api-docs
```

### JWT Configuration (for future use)
```properties
jwt.secret-key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
```

---

## 🧪 Testing the API

### Using curl

```bash
# Get all contacts
curl -X GET http://localhost:8080/api/contacts

# Create contact
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "phone": "(555) 123-4567",
    "address": "123 Main St"
  }'

# Get single contact
curl -X GET http://localhost:8080/api/contacts/1

# Update contact
curl -X PUT http://localhost:8080/api/contacts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe"
  }'

# Delete contact
curl -X DELETE http://localhost:8080/api/contacts/1

# Search contacts
curl -X GET "http://localhost:8080/api/contacts/search?firstName=john"
```

### Using Postman

1. Create new collection "Phonebook"
2. Add requests:
   - GET http://localhost:8080/api/contacts
   - POST http://localhost:8080/api/contacts
   - GET http://localhost:8080/api/contacts/{{id}}
   - PUT http://localhost:8080/api/contacts/{{id}}
   - DELETE http://localhost:8080/api/contacts/{{id}}
   - GET http://localhost:8080/api/contacts/search

3. Use environment variables for `id` and `baseUrl`

---

## 🐛 Troubleshooting

### Issue: CORS Error
**Error**: "Access to XMLHttpRequest has been blocked by CORS policy"

**Solution**: 
- Ensure WebConfig.java is in the classpath
- Check allowed origins match frontend URL
- Verify @Configuration annotation is present
- Restart the backend

### Issue: Connection Refused
**Error**: "Connection refused" when connecting to database

**Solution**:
- Ensure PostgreSQL is running
- Check credentials in application.properties
- Verify database `phonebook` exists
- Check port 5432 is accessible

### Issue: 404 Not Found
**Error**: "404 - Not Found"

**Solution**:
- Check endpoint path matches documentation
- Verify ContactController is scanned by Spring
- Check request method (GET, POST, etc.)
- Verify resource exists (for GET/PUT/DELETE)

### Issue: 400 Bad Request
**Error**: "400 - Bad Request"

**Solution**:
- Check JSON request body format
- Verify required fields are present
- Check Content-Type header is application/json
- Validate data types (phone number format, etc.)

### Issue: 500 Internal Server Error
**Error**: "500 - Internal Server Error"

**Solution**:
- Check backend logs for exception
- Verify database connection
- Check database schema is created
- Restart backend

---

## 📊 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/abiodunelijah/
│   │   │       ├── BackendApplication.java
│   │   │       ├── config/
│   │   │       │   └── WebConfig.java           ← CORS Configuration
│   │   │       └── contact/
│   │   │           ├── controllers/
│   │   │           │   └── ContactController.java
│   │   │           ├── services/
│   │   │           ├── dtos/
│   │   │           ├── entities/
│   │   │           ├── mappers/
│   │   │           └── repositories/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

---

## ✅ Checklist

- ✅ WebConfig.java created
- ✅ CORS configured for localhost:5173
- ✅ ContactController updated with new endpoints
- ✅ Backward compatibility maintained
- ✅ Swagger/OpenAPI support enabled
- ✅ Database configuration set
- ✅ All HTTP methods supported (GET, POST, PUT, DELETE)
- ✅ Error handling in place

---

## 🔗 Integration with Frontend

The frontend is configured to use:

```javascript
// src/services/contactAPI.js
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});
```

This matches the new backend API structure perfectly.

---

## 📝 Summary

The backend is now properly configured with:
1. ✅ CORS enabled for frontend communication
2. ✅ RESTful API endpoints matching frontend expectations
3. ✅ Proper HTTP methods and status codes
4. ✅ Request validation
5. ✅ Error handling
6. ✅ Backward compatibility
7. ✅ API documentation via Swagger

**Status**: ✅ Ready for full-stack integration

---

**Last Updated**: February 27, 2026  
**Version**: 1.0.0  
**Status**: Production Ready

