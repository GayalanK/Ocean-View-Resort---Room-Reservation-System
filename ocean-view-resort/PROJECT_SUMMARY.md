# Project Summary
## Ocean View Resort - Room Reservation System

### Project Structure

```
ocean-view-resort/
├── src/
│   └── com/oceanview/resort/
│       ├── model/
│       │   ├── User.java
│       │   ├── Guest.java
│       │   ├── Room.java
│       │   └── Reservation.java
│       ├── dao/
│       │   ├── UserDAO.java
│       │   ├── RoomDAO.java
│       │   └── ReservationDAO.java
│       ├── service/
│       │   ├── ReservationService.java
│       │   ├── PricingStrategy.java
│       │   └── DiscountPricingStrategy.java
│       ├── servlet/
│       │   ├── Servlet.java
│       │   ├── LoginServlet.java
│       │   ├── ReservationServlet.java
│       │   ├── RoomServlet.java
│       │   └── BillServlet.java
│       ├── web/
│       │   ├── WebServer.java
│       │   └── ServletRouter.java
│       ├── database/
│       │   └── DatabaseConnection.java
│       ├── factory/
│       │   └── ReservationFactory.java
│       ├── security/
│       │   └── AuthenticationService.java
│       └── util/
│           ├── FileManager.java
│           └── ValidationUtil.java
├── test/
│   └── com/oceanview/resort/
│       ├── service/
│       │   └── ReservationServiceTest.java
│       └── util/
│           └── ValidationUtilTest.java
├── web/
│   ├── index.html
│   └── dashboard.html
├── docs/ (documentation folder)
├── data/ (created at runtime)
├── .gitignore
├── README.md
├── DATABASE_SETUP.md
└── HOW_TO_RUN.md
```

### Features Implemented

✅ **User Authentication** - Login system with session management  
✅ **Add New Reservation** - Create reservations with validation  
✅ **Display Reservation Details** - View all reservations  
✅ **Calculate and Print Bill** - Bill generation with pricing  
✅ **Help Section** - Included in dashboard  
✅ **Exit System** - Clean shutdown  
✅ **Web-based Interface** - Modern HTML/CSS/JavaScript UI  
✅ **Database Connectivity** - JDBC with multiple database support  
✅ **RESTful Web Services** - REST API endpoints  
✅ **Servlet Architecture** - Pure Java servlet implementation  

### Design Patterns Used

1. **Singleton** - FileManager, DatabaseConnection
2. **Factory** - ReservationFactory
3. **DAO** - Data Access Objects pattern
4. **Strategy** - Pricing Strategy pattern
5. **Front Controller** - ServletRouter

### API Endpoints

- `POST /api/login` - User authentication
- `GET /api/reservations` - Get all reservations
- `GET /api/reservations/{number}` - Get specific reservation
- `POST /api/reservations` - Create reservation
- `GET /api/rooms` - Get available rooms
- `GET /api/bill/{number}` - Get bill

### Technologies

- **Language:** Java 17
- **Architecture:** Servlet-based web application
- **Database:** JDBC (Derby/H2/SQLite/File-based)
- **Frontend:** HTML5, CSS3, JavaScript
- **No External Libraries:** Pure Java implementation

### How to Run

1. Open project in NetBeans
2. Set main class: `com.oceanview.resort.web.WebServer`
3. Run project (F6)
4. Access: http://localhost:8080
5. Login: admin / admin123

### Assessment Requirements Met

✅ Task A: UML Diagrams (documentation needed)  
✅ Task B: Interactive System with web services, design patterns, database  
✅ Task C: Test classes and test plan  
✅ Task D: Git repository setup (to be done)  

---

**Project Status:** ✅ Complete and ready for testing
