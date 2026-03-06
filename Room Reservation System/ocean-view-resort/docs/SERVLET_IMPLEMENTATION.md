# Servlet Implementation Guide
## Ocean View Resort - Room Reservation System

### Overview
This project implements a servlet-based web application using **pure Java** (no external servlet libraries). The servlet architecture follows the standard Java servlet pattern while using Java's built-in HTTP server capabilities.

### Servlet Architecture

The application uses a custom servlet framework built on top of Java's `ServerSocket` and `Socket` classes. This approach:
- ✅ No external libraries required
- ✅ Pure Java implementation
- ✅ Works with NetBeans IDE
- ✅ Follows servlet patterns (doGet, doPost, service method)
- ✅ RESTful API design

### Servlet Classes

#### 1. **Servlet.java** (Base Servlet Class)
- Abstract base class for all servlets
- Provides `service()`, `doGet()`, `doPost()`, `doPut()`, `doDelete()` methods
- Includes `HttpServletRequest` and `HttpServletResponse` wrapper classes
- Handles HTTP request/response abstraction

**Location:** `src/com/oceanview/resort/servlet/Servlet.java`

#### 2. **LoginServlet.java**
- Handles user authentication
- Endpoint: `POST /api/login` or `POST /login`
- Validates username and password
- Returns session token on success

**Location:** `src/com/oceanview/resort/servlet/LoginServlet.java`

**Example Request:**
```json
POST /api/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Example Response:**
```json
{
  "success": true,
  "session": "sess_1234567890_123456",
  "username": "admin",
  "message": "Login successful"
}
```

#### 3. **ReservationServlet.java**
- Handles all reservation operations
- Endpoints:
  - `GET /api/reservations` - Get all reservations
  - `GET /api/reservations/{number}` - Get specific reservation
  - `GET /api/reservations/search?name={name}` - Search by guest name
  - `POST /api/reservations` - Create new reservation

**Location:** `src/com/oceanview/resort/servlet/ReservationServlet.java`

**Example Request (Create):**
```json
POST /api/reservations
Content-Type: application/json

{
  "name": "John Smith",
  "address": "123 Main St, Colombo",
  "contact": "0712345678",
  "email": "john@example.com",
  "nic": "123456789V",
  "roomNumber": "R101",
  "checkInDate": "2024-12-20",
  "checkOutDate": "2024-12-23"
}
```

**Example Response:**
```json
{
  "success": true,
  "reservationNumber": "RES1001",
  "message": "Reservation created successfully"
}
```

#### 4. **RoomServlet.java**
- Handles room-related operations
- Endpoints:
  - `GET /api/rooms` - Get all available rooms
  - `GET /api/rooms?available=false` - Get all rooms (including unavailable)
  - `GET /api/rooms/{roomNumber}` - Get specific room

**Location:** `src/com/oceanview/resort/servlet/RoomServlet.java`

**Example Response:**
```json
[
  {
    "roomNumber": "R101",
    "roomType": "SINGLE",
    "typeDescription": "Single Room",
    "isAvailable": true,
    "capacity": 1,
    "features": "AC, TV, WiFi",
    "rate": 5000.00
  }
]
```

#### 5. **BillServlet.java**
- Handles bill calculation and generation
- Endpoint: `GET /api/bill/{reservationNumber}`

**Location:** `src/com/oceanview/resort/servlet/BillServlet.java`

**Example Response:**
```json
{
  "reservationNumber": "RES1001",
  "bill": "========================================\n     OCEAN VIEW RESORT - BILL          \n========================================\n..."
}
```

### Servlet Router

**ServletRouter.java** - Routes HTTP requests to appropriate servlets based on URL patterns.

**Location:** `src/com/oceanview/resort/web/ServletRouter.java`

**Routing Logic:**
- `/login` or `/api/login` → LoginServlet
- `/api/reservations/*` or `/reservations/*` → ReservationServlet
- `/api/rooms/*` or `/rooms/*` → RoomServlet
- `/api/bill/*` or `/bill/*` → BillServlet

### Web Server Integration

**WebServer.java** - Main HTTP server that:
1. Listens on port 8080 (configurable)
2. Serves static HTML files (index.html, dashboard.html)
3. Routes API requests to ServletRouter
4. Handles HTTP request parsing
5. Manages request/response handling

**Location:** `src/com/oceanview/resort/web/WebServer.java`

### Database Integration

All servlets interact with the database through DAO (Data Access Object) classes:
- **ReservationDAO** - Database operations for reservations
- **RoomDAO** - Database operations for rooms
- **UserDAO** - Database operations for users

The DAOs automatically use database if JDBC driver is available, otherwise fall back to file-based storage.

See `DATABASE_SETUP.md` for database configuration details.

### Servlet Lifecycle

1. **Initialization** - Servlets are instantiated when ServletRouter is created
2. **Request Processing**:
   - WebServer receives HTTP request
   - Parses method, path, headers, body
   - Routes to ServletRouter
   - ServletRouter calls appropriate servlet
   - Servlet's `service()` method is called
   - `service()` calls `doGet()`, `doPost()`, etc. based on HTTP method
3. **Response Generation** - Servlet writes response using HttpServletResponse
4. **Cleanup** - Resources are cleaned up after response is sent

### Design Patterns Used

1. **Front Controller Pattern** - ServletRouter acts as front controller
2. **Command Pattern** - Each servlet handles specific commands
3. **DAO Pattern** - Data access abstraction
4. **Singleton Pattern** - DatabaseConnection uses singleton

### Testing Servlets

Servlets can be tested by:
1. Running the WebServer
2. Using browser or tools like Postman/curl
3. Sending HTTP requests to servlet endpoints
4. Verifying JSON responses

**Example Test (curl):**
```bash
# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Get all reservations
curl http://localhost:8080/api/reservations

# Create reservation
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","roomNumber":"R101","checkInDate":"2024-12-20","checkOutDate":"2024-12-23"}'
```

### Advantages of This Approach

1. **No External Dependencies** - Pure Java, works out of the box
2. **NetBeans Compatible** - Standard Java project structure
3. **Servlet-like API** - Familiar to Java developers
4. **Lightweight** - No application server needed
5. **Easy Deployment** - Single JAR file deployment

### For Assessment

This implementation demonstrates:
- ✅ **Distributed Application** - RESTful web services
- ✅ **Servlet Architecture** - Standard servlet patterns
- ✅ **Database Connectivity** - JDBC database operations
- ✅ **Web Services** - REST API endpoints
- ✅ **Pure Java** - No external libraries

### File Structure

```
src/com/oceanview/resort/
├── servlet/
│   ├── Servlet.java              (Base servlet class)
│   ├── LoginServlet.java         (Authentication)
│   ├── ReservationServlet.java   (Reservations CRUD)
│   ├── RoomServlet.java          (Room management)
│   └── BillServlet.java          (Bill generation)
├── web/
│   ├── WebServer.java            (HTTP server)
│   └── ServletRouter.java        (Request routing)
├── database/
│   └── DatabaseConnection.java   (JDBC connection)
└── dao/
    ├── ReservationDAO.java       (Database access)
    ├── RoomDAO.java
    └── UserDAO.java
```

### Running the Application

1. Open project in NetBeans
2. Set main class: `com.oceanview.resort.web.WebServer`
3. Run project (F6)
4. Access: http://localhost:8080
5. Login: admin / admin123

---

**Document Version:** 1.0  
**Last Updated:** December 2024
