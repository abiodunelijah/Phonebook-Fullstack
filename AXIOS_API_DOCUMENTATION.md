# Axios API Service Documentation

## Overview

The frontend now uses Axios as the HTTP client library for all backend API communications. This provides:

- ✅ Cleaner code with less boilerplate
- ✅ Automatic request/response transformation
- ✅ Built-in request/response interceptors
- ✅ Better error handling
- ✅ Request timeout handling
- ✅ Easy to test and mock
- ✅ Centralized API configuration

---

## Installation

Axios has been added to `package.json`. Install it with:

```bash
npm install
```

---

## Configuration

### Environment Variables

The API base URL is configured via environment variables:

#### Development (`.env.development`)
```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_ENV=development
```

#### Production (`.env.production`)
```env
VITE_API_BASE_URL=https://api.example.com/api
VITE_APP_ENV=production
```

### File Location

API configuration: `src/services/contactAPI.js`

---

## API Service Methods

### Import the service

```javascript
import contactAPI from "../services/contactAPI";
```

### Available Methods

#### 1. Get All Contacts

```javascript
const response = await contactAPI.getAllContacts();
// Returns: Array of contact objects
```

**Response:**
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

#### 2. Get Single Contact

```javascript
const response = await contactAPI.getContactById(1);
// Returns: Single contact object
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St"
}
```

---

#### 3. Create Contact

```javascript
const contactData = {
  firstName: "John",
  lastName: "Doe",
  phone: "(555) 123-4567",
  address: "123 Main St"
};

const response = await contactAPI.createContact(contactData);
// Returns: Created contact with ID
```

**Request:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St"
}
```

---

#### 4. Update Contact

```javascript
const contactData = {
  firstName: "Jane",
  lastName: "Doe",
  phone: "(555) 987-6543",
  address: "456 Oak Ave"
};

const response = await contactAPI.updateContact(1, contactData);
// Returns: Updated contact object
```

---

#### 5. Delete Contact

```javascript
await contactAPI.deleteContact(1);
// Returns: Success response
```

---

#### 6. Search Contacts (if supported by backend)

```javascript
const response = await contactAPI.searchContacts("john");
// Returns: Array of matching contacts
```

---

## Error Handling

### Basic Error Handling

```javascript
try {
  const response = await contactAPI.getAllContacts();
  // Handle success
} catch (error) {
  // Handle error
  const errorMsg = error.response?.data?.message || error.message;
  console.error("API Error:", errorMsg);
}
```

### Error Structure

```javascript
// Network error (no response from server)
error.message; // "Network Error" or similar

// Server returned error response
error.response.status; // 404, 500, etc.
error.response.data; // Server error message
error.response.headers; // Response headers

// Request timeout
error.code; // "ECONNABORTED"
error.message; // "timeout of 10000ms exceeded"
```

### Common Error Scenarios

| Scenario | Status | How to Handle |
|----------|--------|---------------|
| Network offline | No response | Check connectivity |
| Backend not running | ECONNREFUSED | Start backend on :8080 |
| Invalid request | 400 | Check request data |
| Not found | 404 | Check resource ID |
| Server error | 500 | Check backend logs |
| Timeout | ECONNABORTED | Increase timeout or check server |

---

## Request/Response Interceptors

### Request Interceptor

Automatically logs all outgoing requests:

```javascript
apiClient.interceptors.request.use(
  (config) => {
    console.log("API Request:", config.method.toUpperCase(), config.url);
    return config;
  },
  (error) => {
    console.error("Request Error:", error);
    return Promise.reject(error);
  }
);
```

### Response Interceptor

Automatically logs all responses:

```javascript
apiClient.interceptors.response.use(
  (response) => {
    console.log("API Response:", response.status, response.data);
    return response;
  },
  (error) => {
    console.error("Response Error:", error.response?.status, error.message);
    return Promise.reject(error);
  }
);
```

### Browser Console Output

When making API calls, you'll see:

```
API Request: GET http://localhost:8080/api/contacts
API Response: 200 [Array of contacts]
API Request: POST http://localhost:8080/api/contacts
API Response: 201 {id: 1, firstName: "John", ...}
```

---

## Usage Examples

### In React Components

```javascript
import { useState, useEffect } from "react";
import contactAPI from "../services/contactAPI";

function MyComponent() {
  const [contacts, setContacts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Fetch all contacts on component mount
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await contactAPI.getAllContacts();
        setContacts(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  // Add new contact
  const handleAddContact = async (contactData) => {
    try {
      const response = await contactAPI.createContact(contactData);
      setContacts((prev) => [response.data, ...prev]);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to add contact");
    }
  };

  // Delete contact
  const handleDeleteContact = async (id) => {
    try {
      await contactAPI.deleteContact(id);
      setContacts((prev) => prev.filter((c) => c.id !== id));
    } catch (err) {
      setError("Failed to delete contact");
    }
  };

  // ... JSX ...
}

export default MyComponent;
```

---

## Configuration Options

### Default Configuration

File: `src/services/contactAPI.js`

```javascript
const apiClient = axios.create({
  baseURL: "http://localhost:8080/api", // Base URL for all requests
  headers: {
    "Content-Type": "application/json", // Default header
  },
  timeout: 10000, // 10 second timeout
});
```

### Customizing Configuration

#### Change Base URL

Edit in `contactAPI.js`:

```javascript
const apiClient = axios.create({
  baseURL: "https://your-api-server.com/api",
  // ... rest of config
});
```

#### Change Timeout

```javascript
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  timeout: 20000, // 20 seconds instead of 10
  // ... rest of config
});
```

#### Add Custom Headers

```javascript
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
    "Custom-Header": "value", // Add custom header
  },
});
```

---

## Advanced Features

### Adding Authentication

To add Bearer token authentication, update the request interceptor:

```javascript
apiClient.interceptors.request.use(
  (config) => {
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

### Refresh Token on 401

Add response interceptor for token refresh:

```javascript
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      // Handle token refresh
      // Call refresh endpoint and retry request
    }
    return Promise.reject(error);
  }
);
```

### Add Request Timeout Handling

```javascript
const handleApiCall = async () => {
  try {
    const response = await contactAPI.getAllContacts();
    // Success
  } catch (error) {
    if (error.code === "ECONNABORTED") {
      // Handle timeout
      console.error("Request timed out");
    }
  }
};
```

---

## Testing

### Mock Axios for Testing

```javascript
import { vi } from "vitest";
import contactAPI from "../services/contactAPI";

// Mock the API
vi.mock("../services/contactAPI", () => ({
  default: {
    getAllContacts: vi.fn(),
    createContact: vi.fn(),
    deleteContact: vi.fn(),
  },
}));

// Test example
test("fetches contacts", async () => {
  const mockContacts = [{ id: 1, firstName: "John" }];
  contactAPI.getAllContacts.mockResolvedValueOnce({
    data: mockContacts,
  });

  const result = await contactAPI.getAllContacts();
  expect(result.data).toEqual(mockContacts);
});
```

---

## API Comparison

### Before (Fetch API)

```javascript
const response = await fetch("http://localhost:8080/api/contacts");
if (response.ok) {
  const data = await response.json();
  setContacts(data);
} else {
  setError("Failed to fetch");
}
```

### After (Axios)

```javascript
try {
  const response = await contactAPI.getAllContacts();
  setContacts(response.data);
} catch (error) {
  setError(error.message);
}
```

---

## Benefits of Using Axios

| Benefit | Details |
|---------|---------|
| **Less Boilerplate** | No need for `if (response.ok)` checks |
| **Auto Transform** | JSON parsing happens automatically |
| **Interceptors** | Built-in request/response processing |
| **Error Handling** | Better error structure and handling |
| **Timeout** | Built-in timeout support |
| **Request Cancellation** | Cancel requests with CancelToken |
| **XSRF Protection** | Built-in XSRF token handling |
| **Request/Response** | Easy to log, modify, or validate |

---

## Troubleshooting

### "Cannot find module 'axios'"

**Solution**: Run `npm install` to install dependencies

```bash
npm install
```

### CORS Errors

**Symptoms**: "Access to XMLHttpRequest at 'http://localhost:8080/api/contacts' from origin 'http://localhost:5173' has been blocked by CORS policy"

**Solution**: Enable CORS on backend

```java
// Spring Boot - Add to configuration
@Configuration
public class CorsConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
          .allowedOrigins("http://localhost:5173")
          .allowedMethods("GET", "POST", "PUT", "DELETE")
          .allowCredentials(true);
      }
    };
  }
}
```

### 404 Errors

**Check**:
1. Backend is running on port 8080
2. Endpoint path is correct
3. Resource ID exists

### Timeout Errors

**Solutions**:
1. Increase timeout in `contactAPI.js`
2. Check backend performance
3. Check network connectivity

---

## Summary

✅ Axios is now configured and ready to use  
✅ All API calls are centralized in `src/services/contactAPI.js`  
✅ Request/Response logging is built-in  
✅ Error handling is simplified  
✅ Environment-based configuration is supported  
✅ Easy to extend with authentication or other features  

**Next Step**: Run `npm install` and start the app!

```bash
npm install
npm run dev
```

---

For more information, visit the [Axios Documentation](https://axios-http.com/)

