# Backend & Frontend Integration Testing Guide

## 🧪 Complete Testing Instructions

This guide provides step-by-step instructions to verify that the backend and frontend are properly integrated and working correctly.

---

## ✅ Prerequisites Check

Before testing, ensure you have:

```bash
# Java 25+
java -version
# Expected: openjdk version "25" or higher

# Maven (via mvnw)
./mvnw --version
# Should work without separate Maven installation

# Node 14+
node --version
# Expected: v14.0.0 or higher

# npm
npm --version
# Expected: 6.0.0 or higher

# PostgreSQL
psql --version
# Expected: psql (PostgreSQL) 12 or higher

# Database exists
psql -U coder2client -d phonebook -c "\dt"
# Should list tables or show "Did not find any relations"
```

---

## 🗄️ Phase 1: Database Setup

### 1.1 Create Database

```bash
# Connect to PostgreSQL as admin
psql -U postgres

# In PostgreSQL:
CREATE DATABASE phonebook;

# Verify
\l

# Exit
\q
```

### 1.2 Verify Connection

```bash
# Test connection with credentials
psql -U coder2client -d phonebook -c "SELECT 1"

# Expected output:
# ?column?
# ----------
#        1
```

### 1.3 Check Tables

```bash
# Connect to database
psql -U coder2client -d phonebook

# List tables
\dt

# Expected: initially empty, tables created by Spring when backend starts
```

---

## 🔧 Phase 2: Backend Setup & Testing

### 2.1 Navigate to Backend

```bash
cd backend
```

### 2.2 Clean and Build

```bash
# Clean previous builds
./mvnw clean

# Build project
./mvnw install

# Expected output:
# BUILD SUCCESS
# Total time: X seconds
```

### 2.3 Start Backend Server

```bash
./mvnw spring-boot:run
```

### 2.4 Verify Backend Started

You should see:
```
Started BackendApplication in X.XXX seconds
```

### 2.5 Test Backend Health

In a new terminal:

```bash
# Test API is responding
curl -X GET http://localhost:8080/api/contacts

# Expected:
# []
# (empty array since no contacts yet)
```

### 2.6 Test CORS Configuration

```bash
# Test preflight request
curl -X OPTIONS http://localhost:8080/api/contacts \
  -H "Origin: http://localhost:5173" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type"

# Expected headers in response:
# Access-Control-Allow-Origin: http://localhost:5173
# Access-Control-Allow-Methods: GET, POST, PUT, DELETE, ...
```

### 2.7 Test API Endpoints

#### Create Contact

```bash
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "phone": "(555) 123-4567",
    "address": "123 Main St"
  }'

# Expected response:
# {"id":1,"firstName":"John","lastName":"Doe","phone":"(555) 123-4567","address":"123 Main St"}
```

#### Get All Contacts

```bash
curl -X GET http://localhost:8080/api/contacts

# Expected response:
# [{"id":1,"firstName":"John",...}]
```

#### Get Single Contact

```bash
curl -X GET http://localhost:8080/api/contacts/1

# Expected response:
# {"id":1,"firstName":"John",...}
```

#### Update Contact

```bash
curl -X PUT http://localhost:8080/api/contacts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe"
  }'

# Expected response:
# {"id":1,"firstName":"Jane",...}
```

#### Delete Contact

```bash
curl -X DELETE http://localhost:8080/api/contacts/1

# Expected response:
# "Contact deleted successfully"
```

#### Search Contacts

```bash
curl -X GET "http://localhost:8080/api/contacts/search?firstName=John"

# Expected response:
# [{"id":X,"firstName":"John",...}]
```

### 2.8 Verify Database

```bash
# Connect to database
psql -U coder2client -d phonebook

# List tables
\dt

# Expected:
#           List of relations
#  Schema | Name | Type  | Owner
# --------+------+-------+-------
#  public | contact | table | coder2client
#  public | address | table | coder2client

# Count contacts
SELECT COUNT(*) FROM contact;

# Exit
\q
```

---

## 🎨 Phase 3: Frontend Setup & Testing

### 3.1 Navigate to Frontend

In a new terminal:

```bash
cd frontend
```

### 3.2 Install Dependencies

```bash
npm install

# Expected output:
# added XXX packages
# up to date, audited XXX packages
```

### 3.3 Start Frontend Server

```bash
npm run dev

# Expected output:
# VITE v7.2.4 ready in XXX ms
# ➜  Local:   http://localhost:5173/
```

### 3.4 Verify Frontend Loads

Open in browser:
```
http://localhost:5173
```

You should see:
- Phonebook header with icon
- "Add New Contact" button
- Search bar
- Contact list (empty or with existing contacts)
- Footer

---

## 🔗 Phase 4: Full Integration Testing

### 4.1 Browser DevTools Setup

1. Open http://localhost:5173 in browser
2. Press F12 to open DevTools
3. Go to Console tab
4. Go to Network tab

### 4.2 Test: View Contacts

1. Backend should automatically fetch all contacts on load
2. **Expected logs in Console**:
   ```
   API Request: GET http://localhost:8080/api/contacts
   API Response: 200 [...]
   ```
3. **Expected in Network tab**:
   - Request: `GET /api/contacts`
   - Status: 200
   - Response: Array of contacts

### 4.3 Test: Add Contact

1. Click "Add New Contact"
2. Form expands with fields:
   - First Name (required)
   - Last Name (optional)
   - Phone (required)
   - Address (optional)
3. Fill in:
   - First Name: Alice
   - Last Name: Smith
   - Phone: (555) 987-6543
   - Address: 456 Oak Ave
4. Click "Add Contact"

**Expected behavior**:
- Form collapses
- New contact appears at top of list
- **Console logs**:
  ```
  API Request: POST http://localhost:8080/api/contacts
  API Response: 201 {...}
  ```
- **Network tab**: POST request with status 201

### 4.4 Test: Search

1. In search bar, type: "Alice"
2. Contact list filters in real-time
3. Only Alice's contact shows
4. Type more characters
5. Clear search
6. All contacts show again

**Expected behavior**:
- No API calls (filtering is client-side)
- Console shows: "No API Request" (filtering is local)

### 4.5 Test: Delete Contact

1. Hover over a contact
2. Click red delete icon
3. Contact is removed from list

**Expected behavior**:
- **Console logs**:
  ```
  API Request: DELETE http://localhost:8080/api/contacts/{id}
  API Response: 200 "Contact deleted successfully"
  ```
- **Network tab**: DELETE request with status 200

### 4.6 Test: Responsive Design

1. Press Ctrl+Shift+M to toggle device toolbar
2. Test viewport sizes:
   - Mobile: 375x667
   - Tablet: 768x1024
   - Desktop: 1920x1080

**Expected behavior**:
- Layout adapts to screen size
- Form is single column on mobile
- Buttons are touch-friendly
- No horizontal scrolling

### 4.7 Test: Error Handling

#### Stop Backend

1. In backend terminal, press Ctrl+C
2. In frontend, try to add contact
3. Check console

**Expected behavior**:
- Error message shows: "Unable to connect to server"
- Console shows network error

#### Start Backend Again

1. Run `./mvnw spring-boot:run`
2. Refresh frontend (F5)
3. Contacts load again

**Expected behavior**:
- Contacts visible again
- No error messages

---

## 📊 Phase 5: API Endpoint Verification

### 5.1 Swagger UI

1. Open: http://localhost:8080/swagger-ui.html
2. Should show API endpoints
3. Try operations from Swagger

### 5.2 All Endpoints Test

```bash
# 1. Get all contacts
curl http://localhost:8080/api/contacts

# 2. Create contact
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Bob","lastName":"Johnson","phone":"(555) 111-1111","address":"789 Pine St"}'

# 3. Get contact by ID
curl http://localhost:8080/api/contacts/1

# 4. Update contact
curl -X PUT http://localhost:8080/api/contacts/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Robert"}'

# 5. Delete contact
curl -X DELETE http://localhost:8080/api/contacts/1

# 6. Search contacts
curl "http://localhost:8080/api/contacts/search?firstName=Alice"
```

---

## 🧪 Phase 6: Performance Testing

### 6.1 Load Time

**Frontend**:
```bash
# Use Chrome DevTools
# F12 → Performance tab → Record
# Load page
# Check load metrics
# Target: < 2 seconds
```

**Backend**:
```bash
# Check startup time in logs
# Target: < 5 seconds
```

### 6.2 API Response Time

```bash
# Measure API response time
time curl http://localhost:8080/api/contacts

# Target: < 100ms for typical query
```

### 6.3 Database Query Performance

```bash
# Connect to database
psql -U coder2client -d phonebook

# Check table size
SELECT 
  schemaname,
  tablename,
  pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename))
FROM pg_tables
WHERE schemaname='public';
```

---

## 🔍 Phase 7: Troubleshooting Tests

### 7.1 CORS Issues

**Symptom**: Console error about CORS

**Test**:
```bash
curl -v -X OPTIONS http://localhost:8080/api/contacts \
  -H "Origin: http://localhost:5173" \
  -H "Access-Control-Request-Method: POST"
```

**Expected**:
- Response should include Access-Control headers

**Fix**:
- Ensure WebConfig.java is in config package
- Restart backend
- Clear browser cache

### 7.2 Database Connection Issues

**Symptom**: Backend fails to start with database error

**Test**:
```bash
psql -U coder2client -d phonebook -c "SELECT 1"
```

**Expected**: Should return 1

**Fix**:
- Check PostgreSQL is running
- Verify credentials in application.properties
- Verify database exists

### 7.3 Frontend Can't Reach Backend

**Symptom**: Network errors in console

**Test**:
```bash
curl http://localhost:8080/api/contacts
```

**Expected**: Should return JSON array

**Fix**:
- Check backend is running
- Check port 8080 is accessible
- Check firewall settings

---

## ✅ Final Verification Checklist

- [ ] Database `phonebook` exists
- [ ] Backend starts without errors
- [ ] Frontend starts without errors
- [ ] Can view contacts from backend
- [ ] Can add new contact from frontend
- [ ] Can search contacts (filters in real-time)
- [ ] Can delete contact from frontend
- [ ] No CORS errors in console
- [ ] No network errors in console
- [ ] Responsive design works on mobile
- [ ] Swagger UI shows all endpoints
- [ ] Database tables created automatically
- [ ] API response times < 100ms
- [ ] Frontend load time < 2 seconds
- [ ] Error messages display correctly
- [ ] All HTTP methods work (GET, POST, PUT, DELETE)

---

## 📝 Test Report Template

```
TEST REPORT
===========
Date: [DATE]
Tester: [NAME]

Backend:
- Status: [ ] Running [ ] Failed
- Port: [ ] 8080 working [ ] Not working
- Database: [ ] Connected [ ] Failed

Frontend:
- Status: [ ] Running [ ] Failed
- Port: [ ] 5173 working [ ] Not working
- API Connection: [ ] OK [ ] Failed

API Endpoints:
- GET /api/contacts: [ ] Pass [ ] Fail
- POST /api/contacts: [ ] Pass [ ] Fail
- GET /api/contacts/{id}: [ ] Pass [ ] Fail
- PUT /api/contacts/{id}: [ ] Pass [ ] Fail
- DELETE /api/contacts/{id}: [ ] Pass [ ] Fail
- GET /api/contacts/search: [ ] Pass [ ] Fail

Frontend Features:
- Add Contact: [ ] Pass [ ] Fail
- View Contacts: [ ] Pass [ ] Fail
- Search: [ ] Pass [ ] Fail
- Delete: [ ] Pass [ ] Fail
- Responsive: [ ] Pass [ ] Fail

Issues Found:
- [List any issues]

Recommendations:
- [List improvements]

Overall Status: [ ] PASS [ ] FAIL
```

---

## 🎉 Success Indicators

Your integration is successful when:

1. ✅ Backend starts on port 8080
2. ✅ Frontend starts on port 5173
3. ✅ Frontend loads without errors
4. ✅ Can see contacts in the list
5. ✅ Can add new contacts
6. ✅ Search filters work
7. ✅ Can delete contacts
8. ✅ No CORS errors
9. ✅ No network errors
10. ✅ Database persists data

---

## 📞 Quick Support

| Issue | Check |
|-------|-------|
| Backend won't start | Database running? Correct credentials? |
| Frontend won't load | Backend running on 8080? |
| CORS errors | WebConfig.java in place? Backend restarted? |
| Contacts not showing | Backend API returning data? |
| Search not working | Type in search box? |
| Delete not working | Click red X button? Check console for errors? |

---

**Test Date**: February 27, 2026  
**Status**: ✅ Ready for Testing  
**Last Updated**: 2026-02-27

---

Proceed with testing and report any issues found!

