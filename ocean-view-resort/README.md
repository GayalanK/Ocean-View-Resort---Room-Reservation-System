# Ocean View Resort - Room Reservation System

**Module:** CIS6003 Advanced Programming  
**Assessment:** WRIT1 - Online Room Reservation System  
**Weighting:** 100%  
**Technology:** Pure Java (Java SE 17) - No External Libraries

## Overview

A comprehensive web-based room reservation system for Ocean View Resort in Galle, Sri Lanka. Built entirely with pure Java using servlet architecture and JDBC database connectivity.

## Features

- ✅ User Authentication (Login/Logout)
- ✅ Add New Reservation
- ✅ View Reservation Details
- ✅ Calculate Bill
- ✅ View Available Rooms
- ✅ Search Reservations
- ✅ Help Section
- ✅ Web-based Interface
- ✅ Database Connectivity (JDBC)
- ✅ RESTful Web Services
- ✅ Servlet Architecture

## Project Structure

```
ocean-view-resort/
├── src/
│   └── com/oceanview/resort/
│       ├── model/           # Data models
│       ├── dao/             # Data Access Objects
│       ├── service/         # Business logic
│       ├── servlet/         # Servlet classes
│       ├── web/             # Web server
│       ├── database/        # Database connection
│       ├── factory/         # Factory patterns
│       ├── security/        # Authentication
│       └── util/            # Utilities
├── test/                    # Test classes
├── web/                     # HTML/CSS/JS files
├── data/                    # Data storage (created at runtime)
└── docs/                    # Documentation
```

## Quick Start

### Prerequisites
- Java JDK 17 or higher
- NetBeans IDE 12.0+ (recommended)

### Running the Application

1. **Open in NetBeans:**
   - File → Open Project → Select `ocean-view-resort` folder

2. **Set Main Class:**
   - Right-click project → Properties → Run
   - Main Class: `com.oceanview.resort.web.WebServer`

3. **Run:**
   - Right-click project → Run (F6)

4. **Access in Browser:**
   - Open: http://localhost:8080
   - Login: admin / admin123

## Database Setup

The application supports multiple database options:
- Java DB/Derby (bundled with some JDKs)
- H2 Database
- SQLite
- File-based storage (fallback)

See `DATABASE_SETUP.md` for detailed setup instructions.

## Design Patterns

- Singleton (FileManager, DatabaseConnection)
- Factory (ReservationFactory)
- DAO (Data Access Objects)
- Strategy (Pricing Strategy)
- Front Controller (ServletRouter)

## API Endpoints

- `POST /api/login` - User authentication
- `GET /api/reservations` - Get all reservations
- `GET /api/reservations/{number}` - Get specific reservation
- `POST /api/reservations` - Create reservation
- `GET /api/rooms` - Get available rooms
- `GET /api/bill/{number}` - Get bill

## Documentation

See `docs/` folder for:
- Design Document
- Test Plan
- UML Diagrams
- Servlet Implementation Guide
- Database Setup Guide

## Author

CIS6003 Advanced Programming Assessment
