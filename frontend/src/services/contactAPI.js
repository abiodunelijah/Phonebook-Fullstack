import axios from "axios";

// Create axios instance with default config
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000, // 10 second timeout
});

// Add request interceptor for logging
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

// Add response interceptor for error handling
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

// Contact API endpoints
const contactAPI = {
  // Get all contacts
  getAllContacts: () => apiClient.get("/contacts"),

  // Get single contact by ID
  getContactById: (id) => apiClient.get(`/contacts/${id}`),

  // Create new contact
  createContact: (contactData) => apiClient.post("/contacts", contactData),

  // Update contact
  updateContact: (id, contactData) =>
    apiClient.put(`/contacts/${id}`, contactData),

  // Delete contact
  deleteContact: (id) => apiClient.delete(`/contacts/${id}`),

  // Search contacts (if backend supports it)
  searchContacts: (query) => apiClient.get("/contacts/search", { params: { q: query } }),
};

export default contactAPI;

