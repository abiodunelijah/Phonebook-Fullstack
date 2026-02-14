# Phonebook Application

A full-stack web application for managing contacts with a Spring Boot backend and React frontend.

## 📋 Project Overview

The Phonebook application is a modern contact management system that allows users to create, read, update, and delete contacts with their associated address information. The application features a clean separation between frontend and backend, enabling scalable development and deployment.

## 🏗️ Project Structure

```
Phonebook/
├── backend/              # Spring Boot REST API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/abiodunelijah/
│   │   │   │   ├── contact/
│   │   │   │   │   ├── controllers/    # REST endpoints
│   │   │   │   │   ├── services/       # Business logic
│   │   │   │   │   ├── repositories/   # Data access
│   │   │   │   │   ├── entities/       # JPA entities
│   │   │   │   │   ├── dtos/           # Data transfer objects
│   │   │   │   │   └── mappers/        # Entity-DTO mapping
│   │   │   │   └── exception/          # Global error handling
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml           # Maven configuration
│
├── frontend/             # React + Vite
│   ├── src/
│   │   ├── App.jsx       # Main application component
│   │   ├── main.jsx      # Entry point
│   │   ├── App.css       # Styling
│   │   └── assets/       # Static assets
│   ├── package.json
│   └── vite.config.js
│
└── clients/
    └── client.http       # HTTP client requests
```

## 🚀 Features

- **Contact Management**: Create, read, update, and delete contacts
- **Address Information**: Store detailed address information for each contact
- **Search Functionality**: Search contacts by first name, last name, or phone number
- **RESTful API**: Full-featured REST API with standard HTTP methods
- **Input Validation**: Server-side validation for data integrity
- **Error Handling**: Global exception handling with detailed error responses

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 4.0.2
- **Language**: Java 25
- **Database**: PostgreSQL
- **ORM**: Hibernate JPA
- **Validation**: Spring Validation
- **Build Tool**: Maven
- **Additional**: Lombok for reducing boilerplate

### Frontend
- **Framework**: React 19.2.0
- **Build Tool**: Vite 7.2.4
- **Package Manager**: npm
- **ESLint**: Code quality and linting

## 📦 Prerequisites

Before running the project, ensure you have:

- Java 25 or higher
- Node.js and npm (v14+)
- PostgreSQL (v12+)
- Maven

## 🔧 Installation & Setup

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Configure the database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

   The backend API will be available at `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run the development server:
   ```bash
   npm run dev
   ```

   The frontend will be available at `http://localhost:5173`

4. Build for production:
   ```bash
   npm run build
   ```

## 📡 API Endpoints

All endpoints are prefixed with `/api/v1/contacts`

### Contact Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/add-contact` | Create a new contact |
| GET | `/all-contacts` | Retrieve all contacts |
| GET | `/get-contact/{id}` | Retrieve a specific contact by ID |
| PUT | `/update-contact/{id}` | Update a contact |
| DELETE | `/delete-contact/{id}` | Delete a contact |

### Search

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/search` | Search contacts by firstName, lastName, or phoneNumber |

**Search Example:**
```
GET /api/v1/contacts/search?firstName=john&lastName=Doe&phoneNumber=1234567890
```

## 📝 API Request/Response Examples

### Create Contact
```bash
POST /api/v1/contacts/add-contact
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890",
  "email": "john.doe@example.com",
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001"
  }
}
```

### Response
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890",
  "email": "john.doe@example.com",
  "address": {
    "id": 1,
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001"
  }
}
```

## 🗄️ Database Schema

### Contacts Table
- `id`: Primary key (auto-generated)
- `firstName`: Contact's first name
- `lastName`: Contact's last name
- `phoneNumber`: Contact's phone number
- `email`: Contact's email address
- `address_id`: Foreign key to Address table

### Address Table
- `id`: Primary key (auto-generated)
- `street`: Street address
- `city`: City name
- `state`: State/Province
- `zipCode`: Postal code

## ⚙️ Configuration

### Application Properties

Key configurations in `backend/src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook
spring.datasource.username=coder2client
spring.datasource.password=pastoral2u

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT (for future authentication)
jwt.secret-key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Linting
```bash
cd frontend
npm run lint
```

## 📚 Project Components

### Key Classes

#### Backend
- **Contact Entity**: Represents a contact in the system
- **Address Entity**: Represents address information
- **ContactService**: Business logic for contact operations
- **ContactController**: REST endpoints
- **ContactRepository**: Database queries for contacts
- **GlobalExceptionHandler**: Centralized error handling

#### Frontend
- **App.jsx**: Main application component
- **App.css**: Application styling

## 🔐 Security Notes

- **JWT Configuration**: JWT support is configured but commented out in `pom.xml`
- **Validation**: Input validation is enabled through Spring Validation
- **Database Credentials**: Update default credentials in `application.properties` for production use

## 🚀 Future Enhancements

- [ ] Implement JWT authentication and authorization
- [ ] Add user profile management
- [ ] Implement contact groups/categories
- [ ] Add profile picture support
- [ ] Implement export/import functionality (CSV, vCard)
- [ ] Add pagination and sorting
- [ ] Implement caching for better performance
- [ ] Add real-time notifications
- [ ] Implement contact backup and recovery

## 📖 Development Tips

1. **Backend Development**: Use the provided `clients/client.http` file for testing API endpoints
2. **Hot Reload**: Both frontend and backend support hot reload during development
3. **Logging**: Check console output for detailed logging information
4. **Database**: Ensure PostgreSQL is running before starting the backend

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit your changes: `git commit -m 'Add your feature'`
3. Push to the branch: `git push origin feature/your-feature`
4. Submit a pull request

## 📄 License

This project is provided as-is for educational and personal use.

## 👨‍💻 Author

**Abiodun Elijah**

---

## 📞 Support

For issues, questions, or suggestions, please open an issue in the project repository.

---

**Last Updated**: February 2026

