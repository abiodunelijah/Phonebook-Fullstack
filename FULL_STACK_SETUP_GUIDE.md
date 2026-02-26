# Full Stack Setup & Deployment Guide

## 🚀 Complete Application Flow

This guide covers setting up and running the entire Phonebook application (frontend + backend).

---

## 📋 Prerequisites

### System Requirements
- Java 25+ (for backend)
- Node.js 14+ (for frontend)
- npm or yarn (for frontend)
- PostgreSQL 12+ (for database)
- Maven (for building backend)

### Installation Check

```bash
# Check Java version
java -version

# Check Node version
node -version
npm -version

# Check PostgreSQL (if installed)
psql --version
```

---

## 🗄️ Database Setup

### 1. Create PostgreSQL Database

```bash
# Connect to PostgreSQL
psql -U postgres

# In PostgreSQL prompt:
CREATE DATABASE phonebook;

# Verify creation
\l

# Exit
\q
```

### 2. Update Database Credentials

**File**: `backend/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**Default values in project**:
- Username: `coder2client`
- Password: `pastoral2u`

If using different credentials, update the properties file accordingly.

### 3. Verify Connection

```bash
# Test connection
psql -U coder2client -d phonebook -c "SELECT 1"
```

---

## ⚙️ Backend Setup

### Step 1: Navigate to Backend Directory

```bash
cd backend
```

### Step 2: Build Backend

```bash
# Using Maven wrapper (no Maven installation needed)
./mvnw clean install

# Or on Windows
mvnw.cmd clean install
```

### Step 3: Run Backend

```bash
./mvnw spring-boot:run

# Or on Windows
mvnw.cmd spring-boot:run
```

### Expected Output

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v4.0.2)

Started BackendApplication in 2.5 seconds
```

### Backend Running On
```
http://localhost:8080
```

### API Endpoints Available
```
GET    http://localhost:8080/api/contacts
POST   http://localhost:8080/api/contacts
GET    http://localhost:8080/api/contacts/{id}
PUT    http://localhost:8080/api/contacts/{id}
DELETE http://localhost:8080/api/contacts/{id}
GET    http://localhost:8080/api/contacts/search
```

### Swagger UI (API Documentation)
```
http://localhost:8080/swagger-ui.html
```

---

## 🎨 Frontend Setup

### Step 1: Navigate to Frontend Directory

```bash
cd frontend
```

### Step 2: Install Dependencies

```bash
npm install
```

This installs:
- React 19.2.0
- Vite 7.2.4
- Axios 1.6.0
- And other dependencies

### Step 3: Run Frontend Development Server

```bash
npm run dev
```

### Expected Output

```
  VITE v7.2.4  ready in XXX ms

  ➜  Local:   http://localhost:5173/
  ➜  press h to show help
```

### Frontend Running On
```
http://localhost:5173
```

### Available Commands

```bash
# Development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint
```

---

## ✅ Verification Checklist

### Backend Verification

- [ ] PostgreSQL database exists and is accessible
- [ ] Backend starts without errors
- [ ] Console shows "Started BackendApplication"
- [ ] Swagger UI accessible at http://localhost:8080/swagger-ui.html
- [ ] Can ping API: `curl http://localhost:8080/api/contacts`

### Frontend Verification

- [ ] Dependencies installed successfully
- [ ] Development server starts without errors
- [ ] Browser shows "Phonebook" application
- [ ] Can see form to add contacts
- [ ] Can see search bar
- [ ] Can see contact list area (empty initially)

### Integration Verification

- [ ] Open browser DevTools (F12)
- [ ] Go to Console tab
- [ ] See API logs when loading
- [ ] Check Network tab for API requests
- [ ] Try adding a contact
- [ ] Verify contact appears in list
- [ ] Try searching for contact
- [ ] Try deleting contact

---

## 🧪 Manual Testing

### Test 1: Add Contact

1. Open http://localhost:5173
2. Click "Add New Contact"
3. Fill in:
   - First Name: John
   - Last Name: Doe
   - Phone: (555) 123-4567
   - Address: 123 Main St
4. Click "Add Contact"
5. Verify contact appears in list

### Test 2: View Contacts

1. Contacts should be displayed in cards
2. Each card shows:
   - First and Last Name
   - Phone number with icon
   - Address with icon
   - Delete button

### Test 3: Search

1. Type in search box
2. List filters in real-time
3. Search by:
   - First name: "John"
   - Last name: "Doe"
   - Phone: "(555)"
   - Address: "Main"

### Test 4: Delete Contact

1. Click delete icon on any contact
2. Contact is removed from list
3. Verify in database (count decreases)

### Test 5: Responsive Design

1. Open DevTools (F12)
2. Toggle device toolbar (Ctrl+Shift+M)
3. Test on:
   - Mobile (< 480px)
   - Tablet (480-768px)
   - Desktop (> 768px)
4. Verify layout adjusts properly

---

## 📊 Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│           PHONEBOOK FULL-STACK APPLICATION              │
└─────────────────────────────────────────────────────────┘

┌──────────────────────────────┐    ┌──────────────────────────────┐
│      FRONTEND (React)         │    │     BACKEND (Spring Boot)    │
│   http://localhost:5173       │    │   http://localhost:8080      │
├──────────────────────────────┤    ├──────────────────────────────┤
│ • App.jsx                    │    │ • BackendApplication.java    │
│ • Header/Header.jsx          │    │ • ContactController.java     │
│ • Main/Main.jsx              │    │ • WebConfig.java (CORS)      │
│ • Footer/Footer.jsx          │    │ • ContactService.java        │
│ • services/contactAPI.js     │    │ • ContactRepository.java     │
│                              │    │                              │
│ TECHNOLOGY STACK:            │    │ TECHNOLOGY STACK:            │
│ • React 19.2.0              │    │ • Spring Boot 4.0.2         │
│ • Vite 7.2.4                │    │ • Spring Data JPA           │
│ • Axios 1.6.0               │    │ • PostgreSQL                │
│ • CSS3                       │    │ • Lombok                    │
└──────────┬───────────────────┘    └───────┬──────────────────────┘
           │                               │
           └───────────────────┬───────────┘
                               │
                        HTTP (REST API)
                    CORS Enabled (Port 8080)
                               │
                    ┌──────────┴─────────┐
                    │                    │
                ┌───▼─────┐         ┌───▼─────┐
                │   GET   │         │  POST   │
                │   PUT   │         │ DELETE  │
                └─────────┘         └─────────┘
                    │                    │
                    └──────────┬─────────┘
                               │
                  ┌────────────▼────────────┐
                  │  PostgreSQL Database    │
                  │  (phonebook)            │
                  │ • contacts table        │
                  │ • addresses table       │
                  └─────────────────────────┘
```

---

## 🔄 API Communication Flow

```
1. Frontend (React Component)
   ↓
2. contactAPI.js (Axios Service)
   ↓
3. HTTP Request to http://localhost:8080/api/contacts
   ↓
4. Backend (ContactController)
   ↓
5. ContactService (Business Logic)
   ↓
6. ContactRepository (Database Query)
   ↓
7. PostgreSQL Database
   ↓
8. Response (JSON)
   ↓
9. Frontend (Update State)
   ↓
10. React Re-render (UI Update)
```

---

## 📝 Environment Configuration

### Frontend (.env files)

**Development** (`frontend/.env.development`):
```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_ENV=development
```

**Production** (`frontend/.env.production`):
```env
VITE_API_BASE_URL=https://your-production-api.com/api
VITE_APP_ENV=production
```

### Backend (application.properties)

**Development** (`backend/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook
spring.datasource.username=coder2client
spring.datasource.password=pastoral2u
spring.jpa.hibernate.ddl-auto=update
```

**Production** (change as needed):
```properties
spring.datasource.url=jdbc:postgresql://prod-db-host:5432/phonebook
spring.datasource.username=prod_user
spring.datasource.password=prod_password
spring.jpa.hibernate.ddl-auto=validate
```

---

## 🚀 Production Deployment

### Frontend Build

```bash
cd frontend
npm run build
```

This creates an optimized build in the `dist/` folder.

### Deploy Frontend

Options:
1. **Netlify**: Drag & drop `dist` folder
2. **Vercel**: Connect GitHub repository
3. **AWS S3**: Upload `dist` files
4. **Traditional Server**: Copy `dist` to web server

### Deploy Backend

```bash
cd backend
./mvnw clean package
```

This creates `target/backend-0.0.1-SNAPSHOT.jar`

Deploy to:
- AWS EC2
- Heroku
- DigitalOcean
- Docker
- Traditional Server

---

## 🐛 Troubleshooting

### Frontend Issues

| Issue | Solution |
|-------|----------|
| "Cannot find module 'axios'" | Run `npm install` |
| CORS error in console | Ensure backend is running on :8080 |
| 404 API errors | Check backend is running |
| Port 5173 in use | Change port or kill process using it |
| Blank page | Check browser console for errors |

### Backend Issues

| Issue | Solution |
|-------|----------|
| "Connection refused" | Start PostgreSQL |
| "Database does not exist" | Create database `phonebook` |
| "Failed to connect to database" | Check credentials |
| Port 8080 in use | Kill process or change port |
| "Cannot find class" errors | Run `./mvnw clean install` |

### Database Issues

| Issue | Solution |
|-------|----------|
| Cannot connect to PostgreSQL | Ensure PostgreSQL is running |
| Wrong credentials | Update application.properties |
| Tables not created | Wait for Spring to initialize |
| Data not persisting | Check database connection |

---

## 📊 Health Check Commands

```bash
# Check if backend is running
curl http://localhost:8080/api/contacts

# Check if frontend is running
curl http://localhost:5173

# Check database connection
psql -U coder2client -d phonebook -c "SELECT COUNT(*) FROM contact;"

# View backend logs
./mvnw spring-boot:run

# View frontend logs
npm run dev
```

---

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| BACKEND_CONFIGURATION.md | Backend setup & API docs |
| AXIOS_API_DOCUMENTATION.md | Frontend API service docs |
| AXIOS_INTEGRATION_GUIDE.md | Frontend integration guide |
| QUICK_START.md | Quick setup for frontend |
| QUICK_REFERENCE.md | Code reference card |
| IMPLEMENTATION_SUMMARY.md | Project overview |

---

## 🎯 Next Steps

1. **Setup Database**
   ```bash
   # Create phonebook database in PostgreSQL
   ```

2. **Start Backend**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

3. **Start Frontend**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

4. **Test Application**
   - Open http://localhost:5173
   - Add some contacts
   - Test search and delete

5. **Monitor Logs**
   - Frontend console (Browser DevTools)
   - Backend console (Terminal)
   - Database logs (PostgreSQL)

---

## ✅ Deployment Checklist

- [ ] Database created and accessible
- [ ] Backend builds successfully
- [ ] Frontend builds successfully
- [ ] API endpoints tested with curl/Postman
- [ ] Frontend connects to backend
- [ ] All CRUD operations work
- [ ] Responsive design tested
- [ ] Error handling works
- [ ] No console errors or warnings
- [ ] Performance acceptable

---

## 📞 Support

For issues:
1. Check relevant documentation file
2. Review error messages in console
3. Check network tab in DevTools
4. Verify all prerequisites are met
5. Try restarting services

---

## 🎉 Conclusion

Your Phonebook application is ready for development and deployment!

**Frontend**: http://localhost:5173  
**Backend**: http://localhost:8080  
**Database**: PostgreSQL on localhost:5432

**Status**: ✅ Ready to Use

---

**Last Updated**: February 27, 2026  
**Version**: 1.0.0  
**Status**: Production Ready

