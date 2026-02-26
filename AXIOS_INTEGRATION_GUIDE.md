# Axios Integration Guide

## Quick Start

### 1. Install Dependencies

```bash
cd frontend
npm install
```

This will install axios along with other dependencies.

---

## File Structure

```
frontend/
├── src/
│   ├── services/
│   │   └── contactAPI.js      ← Axios API service
│   ├── Main/
│   │   ├── Main.jsx           ← Uses contactAPI
│   │   └── Main.css
│   ├── App.jsx
│   └── index.css
├── .env.development           ← Dev environment variables
├── .env.production            ← Production environment variables
├── package.json               ← Updated with axios
└── ...
```

---

## What Changed

### 1. Added Axios Package

**File**: `package.json`

```json
{
  "dependencies": {
    "axios": "^1.6.0",  // ← NEW
    "react": "^19.2.0",
    "react-dom": "^19.2.0"
  }
}
```

### 2. Created API Service

**File**: `src/services/contactAPI.js`

Centralized API calls with:
- ✅ Axios instance configuration
- ✅ Request interceptor (logging)
- ✅ Response interceptor (logging)
- ✅ Timeout handling
- ✅ All contact endpoints

### 3. Updated Main Component

**File**: `src/Main/Main.jsx`

Replaced:
- ❌ `fetch()` → ✅ `contactAPI.getAllContacts()`
- ❌ `fetch()` → ✅ `contactAPI.createContact()`
- ❌ `fetch()` → ✅ `contactAPI.deleteContact()`

### 4. Environment Configuration

**Files**:
- `.env.development` - Development API URL
- `.env.production` - Production API URL

---

## How It Works

### Before (Fetch API)

```javascript
try {
  const response = await fetch("http://localhost:8080/api/contacts");
  if (response.ok) {
    const data = await response.json();
    setContacts(data);
  } else {
    setError("Failed to fetch");
  }
} catch (err) {
  setError("Error: " + err.message);
}
```

### After (Axios)

```javascript
import contactAPI from "../services/contactAPI";

try {
  const response = await contactAPI.getAllContacts();
  setContacts(response.data);
} catch (err) {
  setError(err.response?.data?.message || err.message);
}
```

**Benefits**:
- ✅ Less code
- ✅ Cleaner error handling
- ✅ Automatic JSON parsing
- ✅ Built-in request logging
- ✅ Timeout handling
- ✅ Easy to test and mock

---

## API Service Reference

### Import

```javascript
import contactAPI from "../services/contactAPI";
```

### Methods

```javascript
// Get all contacts
const response = await contactAPI.getAllContacts();

// Get single contact
const response = await contactAPI.getContactById(1);

// Create contact
const response = await contactAPI.createContact({
  firstName: "John",
  lastName: "Doe",
  phone: "(555) 123-4567",
  address: "123 Main St"
});

// Update contact
const response = await contactAPI.updateContact(1, {
  firstName: "Jane",
  lastName: "Doe"
});

// Delete contact
await contactAPI.deleteContact(1);

// Search contacts
const response = await contactAPI.searchContacts("john");
```

---

## Error Handling

### Basic Error Handling

```javascript
try {
  const response = await contactAPI.getAllContacts();
  console.log(response.data); // Array of contacts
} catch (error) {
  // Error from server
  if (error.response) {
    const status = error.response.status;
    const message = error.response.data?.message;
    console.error(`Error ${status}: ${message}`);
  }
  // Network error
  else if (error.request) {
    console.error("No response from server");
  }
  // Other error
  else {
    console.error("Error:", error.message);
  }
}
```

### Error Types

```javascript
// Server error response (4xx, 5xx)
error.response.status;   // 400, 404, 500, etc.
error.response.data;     // Error message from server

// Network error (no response)
error.request;          // Request object

// Other errors
error.message;          // Error message
```

---

## Environment Variables

### Development

**File**: `.env.development`

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_ENV=development
```

**Usage in code**:

```javascript
const baseUrl = import.meta.env.VITE_API_BASE_URL;
// Returns: "http://localhost:8080/api"
```

### Production

**File**: `.env.production`

Update before deploying:

```env
VITE_API_BASE_URL=https://your-production-api.com/api
VITE_APP_ENV=production
```

### Adding Custom Variables

1. Create variable in `.env.development`:
   ```env
   VITE_API_TIMEOUT=10000
   ```

2. Use in code:
   ```javascript
   const timeout = parseInt(import.meta.env.VITE_API_TIMEOUT);
   ```

---

## Configuration

### Default Configuration

Location: `src/services/contactAPI.js`

```javascript
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000, // 10 seconds
});
```

### Changing Configuration

**Option 1**: Edit environment variable

```env
VITE_API_BASE_URL=https://new-api.com/api
```

**Option 2**: Edit contactAPI.js directly

```javascript
const apiClient = axios.create({
  baseURL: "https://your-api.com/api",
  timeout: 20000, // Change timeout
  headers: {
    "Content-Type": "application/json",
    "Custom-Header": "value", // Add headers
  },
});
```

---

## Extending the API Service

### Add New Endpoint

**File**: `src/services/contactAPI.js`

```javascript
const contactAPI = {
  // ...existing methods...

  // New method
  getContactsByCategory: (category) =>
    apiClient.get("/contacts/category", { params: { cat: category } }),

  // Another new method
  bulkDelete: (ids) =>
    apiClient.post("/contacts/bulk-delete", { ids }),
};
```

### Use New Method

```javascript
import contactAPI from "../services/contactAPI";

// In your component
const contacts = await contactAPI.getContactsByCategory("friends");
```

---

## Adding Authentication

### Add Bearer Token

**Edit**: `src/services/contactAPI.js`

```javascript
apiClient.interceptors.request.use(
  (config) => {
    // Get token from localStorage
    const token = localStorage.getItem("authToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    console.log("API Request:", config.method.toUpperCase(), config.url);
    return config;
  },
  (error) => {
    console.error("Request Error:", error);
    return Promise.reject(error);
  }
);
```

### Store Token

```javascript
// After login
const token = "your-auth-token";
localStorage.setItem("authToken", token);
```

### Check Token

```javascript
// Before making API calls
const token = localStorage.getItem("authToken");
if (!token) {
  // Redirect to login
  window.location.href = "/login";
}
```

---

## Debugging

### Enable Console Logging

The API service automatically logs all requests and responses in the browser console.

Open DevTools (F12) and check the Console tab:

```
API Request: GET http://localhost:8080/api/contacts
API Response: 200 [Array]
```

### Network Tab

Check the Network tab in DevTools to see:
- Request method (GET, POST, DELETE)
- Request URL
- Request headers
- Response status
- Response body
- Request/response timing

### Troubleshooting

| Issue | Solution |
|-------|----------|
| CORS error | Enable CORS on backend |
| 404 error | Check endpoint path and resource ID |
| 500 error | Check backend logs |
| Timeout | Increase timeout in contactAPI.js |
| Connection refused | Start backend on port 8080 |

---

## Best Practices

### 1. Always Handle Errors

```javascript
try {
  const response = await contactAPI.getAllContacts();
  setContacts(response.data);
} catch (error) {
  setError("Failed to fetch contacts");
  console.error(error);
}
```

### 2. Use Loading States

```javascript
const [loading, setLoading] = useState(false);

try {
  setLoading(true);
  const response = await contactAPI.getAllContacts();
  setContacts(response.data);
} finally {
  setLoading(false);
}
```

### 3. Validate Input

```javascript
if (!firstName.trim() || !phone.trim()) {
  setError("Required fields missing");
  return;
}

const response = await contactAPI.createContact({
  firstName: firstName.trim(),
  phone: phone.trim(),
});
```

### 4. Use Environment Variables

```javascript
// Good ✅
const url = import.meta.env.VITE_API_BASE_URL;

// Bad ❌
const url = "http://localhost:8080/api";
```

### 5. Centralize API Calls

```javascript
// Good ✅
import contactAPI from "../services/contactAPI";
const data = await contactAPI.getAllContacts();

// Bad ❌
const data = await fetch("http://localhost:8080/api/contacts");
```

---

## Testing the Integration

### Step 1: Install Dependencies

```bash
npm install
```

### Step 2: Start Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Step 3: Start Frontend

```bash
cd frontend
npm run dev
```

### Step 4: Open Browser

Go to: http://localhost:5173

### Step 5: Test Features

1. Check Console for API logs
2. Add a new contact
3. Search contacts
4. Delete a contact
5. Check Network tab for requests

---

## Next Steps

1. ✅ Run `npm install` to install axios
2. ✅ Start the backend server
3. ✅ Start the frontend dev server
4. ✅ Open browser console to see API logs
5. ✅ Test adding/deleting contacts
6. ✅ Check Network tab for requests

---

## Summary

✅ **Axios installed** - Dependency added  
✅ **API service created** - Centralized API calls  
✅ **Main component updated** - Using Axios service  
✅ **Environment variables** - Development & production configs  
✅ **Request/Response logging** - Built-in debugging  
✅ **Error handling** - Improved error messages  

**Status**: Ready to use! 🚀

---

For detailed information, see `AXIOS_API_DOCUMENTATION.md`

