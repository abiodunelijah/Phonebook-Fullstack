# Backend Startup Guide - Quick Troubleshooting

## Issue: "Unable to connect to server. Make sure the backend is running on http://localhost:8080"

This error means the frontend cannot reach the backend. Follow this guide to fix it.

---

## ✅ Fixed Issues

### 1. Address Entity Class Name ✅ FIXED
**Problem**: Class was named `AddressAddress` instead of `Address`  
**Solution**: Renamed to `Address`  
**Status**: ✅ Fixed

### 2. Validation Constraints ✅ FIXED
**Problem**: 
- `lastName` had `@NotBlank` but should be optional
- `phoneNumber` pattern was too strict

**Solution**:
- Made `lastName` optional
- Updated `phoneNumber` pattern to accept various formats
- Added `@NotBlank` to `phoneNumber`

**Status**: ✅ Fixed

---

## 🚀 Step-by-Step Backend Startup

### Step 1: Navigate to Backend Directory

```bash
cd backend
```

### Step 2: Clean Previous Build

```bash
./mvnw clean
```

On Windows:
```bash
mvnw.cmd clean
```

### Step 3: Build the Project

```bash
./mvnw install
```

On Windows:
```bash
mvnw.cmd install
```

**Expected Output**:
```
BUILD SUCCESS
Total time: X seconds
```

### Step 4: Start the Backend Server

```bash
./mvnw spring-boot:run
```

On Windows:
```bash
mvnw.cmd spring-boot:run
```

**Expected Output**:
```
Started BackendApplication in X.XXX seconds (JVM running for X.XXXs)
```

### Step 5: Verify Backend is Running

Open a new terminal and run:

```bash
curl http://localhost:8080/api/contacts
```

Expected response:
```json
[]
```

(Empty array, which is correct if no contacts exist)

---

## ✅ Prerequisites Checklist

Before starting, ensure:

- ✅ Java 25+ installed
  ```bash
  java -version
  ```

- ✅ PostgreSQL running
  ```bash
  # Check if PostgreSQL is running on port 5432
  ```

- ✅ Database `phonebook` exists
  ```bash
  psql -U postgres -l | grep phonebook
  ```

- ✅ Database credentials correct in `application.properties`
  ```
  Username: coder2client
  Password: pastoral2u
  ```

---

## 🔧 Common Issues & Solutions

### Issue 1: "Connection refused" or "Unable to connect to database"

**Cause**: PostgreSQL is not running

**Solution**:
1. Start PostgreSQL service
   - Windows: Services → PostgreSQL → Start
   - macOS: `brew services start postgresql`
   - Linux: `sudo systemctl start postgresql`

2. Verify connection:
   ```bash
   psql -U coder2client -d phonebook -c "SELECT 1"
   ```

### Issue 2: "database phonebook does not exist"

**Cause**: Database not created

**Solution**:
```bash
psql -U postgres
CREATE DATABASE phonebook;
\q
```

### Issue 3: "Failed to bind to port 8080"

**Cause**: Another process is using port 8080

**Solution**:
Find and kill the process using port 8080:

Windows:
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

macOS/Linux:
```bash
lsof -i :8080
kill -9 <PID>
```

### Issue 4: Maven build fails with "Cannot find symbol"

**Cause**: Java code has errors

**Solution**:
1. Clean the build:
   ```bash
   ./mvnw clean
   ```

2. Rebuild:
   ```bash
   ./mvnw install
   ```

3. Check error messages carefully
4. Verify all entity and DTO classes exist

### Issue 5: Port 5432 (Database) already in use

**Cause**: PostgreSQL connection issue

**Solution**:
```bash
# Kill existing PostgreSQL connections
psql -U postgres -c "SELECT pid, datname FROM pg_stat_activity WHERE datname = 'phonebook';"

# Then restart PostgreSQL
```

---

## 📝 Application Configuration

**File**: `backend/src/main/resources/application.properties`

Verify these settings:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook
spring.datasource.username=coder2client
spring.datasource.password=pastoral2u
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080

# API Documentation
springdoc.api-docs.path=/api-docs
```

---

## 🧪 Verify Backend Works

After starting backend, test these endpoints:

### 1. Get All Contacts
```bash
curl http://localhost:8080/api/contacts
```

Expected: `[]` (empty array)

### 2. API Documentation
Open in browser:
```
http://localhost:8080/swagger-ui.html
```

### 3. Create Test Contact
```bash
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "(555) 123-4567",
    "addressRequestDto": {
      "streetNumber": "123",
      "streetName": "Main St",
      "postalCode": "12345",
      "state": "CA",
      "country": "USA"
    }
  }'
```

Expected: Contact object with ID

### 4. Get All Contacts Again
```bash
curl http://localhost:8080/api/contacts
```

Expected: Array with the contact you just created

---

## 🔄 Complete Startup Sequence

### Terminal 1: Backend
```bash
cd backend
./mvnw spring-boot:run

# Wait for "Started BackendApplication in X seconds"
```

### Terminal 2: Verify Backend
```bash
curl http://localhost:8080/api/contacts
# Should return: []
```

### Terminal 3: Frontend
```bash
cd frontend
npm run dev

# Should show: "Local: http://localhost:5173"
```

### Terminal 4: Open Browser
```
http://localhost:5173
```

---

## ✅ Success Indicators

Backend is running successfully when:

1. ✅ Console shows: `Started BackendApplication in X seconds`
2. ✅ `curl http://localhost:8080/api/contacts` returns `[]`
3. ✅ No error messages in console
4. ✅ Swagger UI accessible at `http://localhost:8080/swagger-ui.html`
5. ✅ Frontend can connect and display contacts

---

## 📊 Backend Health Check

Run this command to fully verify backend:

```bash
#!/bin/bash

echo "1. Checking Java..."
java -version

echo "2. Checking PostgreSQL..."
psql -U coder2client -d phonebook -c "SELECT 1"

echo "3. Checking Backend API..."
curl http://localhost:8080/api/contacts

echo "4. Checking Swagger UI..."
curl -s http://localhost:8080/swagger-ui.html | head -20

echo "All checks complete!"
```

---

## 🎯 Fixed Files

| File | Issue | Solution |
|------|-------|----------|
| Address.java | Class named AddressAddress | Renamed to Address |
| ContactRequestDto.java | lastName required | Made optional |
| ContactRequestDto.java | Phone pattern too strict | Updated regex pattern |
| Contact.java | lastName not nullable | Made nullable |

---

## 📞 Support

If you still get "Unable to connect to server":

1. **Verify backend is running**
   ```bash
   curl http://localhost:8080/api/contacts
   ```

2. **Check error messages**
   - Look at backend terminal for Java errors
   - Check browser console for JavaScript errors

3. **Verify CORS is configured**
   - WebConfig.java is in `config` package
   - Allows `http://localhost:5173`

4. **Verify database connection**
   ```bash
   psql -U coder2client -d phonebook -c "SELECT 1"
   ```

5. **Check firewall**
   - Ensure port 8080 is open
   - Ensure port 5432 is accessible

---

## 🚀 Next Steps

1. ✅ Clean and rebuild: `./mvnw clean install`
2. ✅ Start backend: `./mvnw spring-boot:run`
3. ✅ Verify with curl: `curl http://localhost:8080/api/contacts`
4. ✅ Start frontend: `npm run dev`
5. ✅ Open: `http://localhost:5173`
6. ✅ Test: Add, view, search, delete contacts

---

**Backend fixes complete! Ready for startup.** 🎉


