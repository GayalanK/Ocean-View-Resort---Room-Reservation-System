# Project Structure
## Ocean View Resort - Room Reservation System

## Complete Directory Tree

```
ocean-view-resort/
│
├── src/                                    # Source Code Directory
│   └── com/
│       └── oceanview/
│           └── resort/
│               │
│               ├── main/                   # Main Application Entry Points
│               │   ├── ReservationSystemMain.java
│               │   └── WebServiceMain.java
│               │
│               ├── model/                  # Domain Models (4 classes)
│               │   ├── User.java
│               │   ├── Guest.java
│               │   ├── Room.java
│               │   └── Reservation.java
│               │
│               ├── dao/                    # Data Access Object Layer (3 classes)
│               │   ├── UserDAO.java
│               │   ├── RoomDAO.java
│               │   └── ReservationDAO.java
│               │
│               ├── service/                # Business Logic Layer (5 classes)
│               │   ├── PricingStrategy.java
│               │   ├── StandardPricingStrategy.java
│               │   ├── DiscountPricingStrategy.java
│               │   ├── BillingService.java
│               │   └── ReservationService.java
│               │
│               ├── factory/                # Factory Pattern (2 classes)
│               │   ├── ReservationFactory.java
│               │   └── RoomFactory.java
│               │
│               ├── ui/                     # User Interface (1 class)
│               │   └── MenuSystem.java
│               │
│               ├── webservice/             # Web Services Layer (1 class)
│               │   └── ReservationWebService.java
│               │
│               ├── util/                   # Utility Classes (2 classes)
│               │   ├── FileManager.java
│               │   └── ValidationUtil.java
│               │
│               └── security/               # Security/Authentication (1 class)
│                   └── AuthenticationService.java
│
├── test/                                   # Test Classes Directory
│   └── com/
│       └── oceanview/
│           └── resort/
│               ├── service/
│               │   └── ReservationServiceTest.java
│               └── util/
│                   └── ValidationUtilTest.java
│
├── data/                                   # Data Storage Directory (created at runtime)
│   ├── users.dat                          # Serialized User objects
│   ├── rooms.dat                          # Serialized Room objects
│   └── reservations.dat                   # Serialized Reservation objects
│
├── docs/                                   # Documentation Directory
│   ├── DESIGN_DOCUMENT.md
│   ├── TEST_PLAN.md
│   ├── UML_DIAGRAMS.md
│   ├── STEP_BY_STEP_IMPLEMENTATION.md
│   └── IMPLEMENTATION_CHECKLIST.md
│
├── build/                                  # Compiled Classes (created on build)
│   └── classes/
│       └── com/
│           └── oceanview/
│               └── resort/
│                   └── [all compiled .class files]
│
├── dist/                                   # Distribution Directory (JAR files)
│   └── Ocean View Resort - Room Reservation System.jar
│
├── .github/                                # GitHub Configuration
│   └── workflows/
│       └── ci.yml                         # CI/CD Pipeline Configuration
│
├── nbproject/                              # NetBeans Project Files
│   ├── project.xml                        # Project Configuration
│   ├── project.properties                 # Project Properties
│   └── build-impl.xml                     # Build Implementation
│
├── README.md                               # Project Overview
├── QUICK_START.md                         # Getting Started Guide
├── PROJECT_SUMMARY.md                     # Project Summary
├── PROJECT_STRUCTURE.md                   # This File
├── GIT_WORKFLOW.md                        # Version Control Documentation
├── build.xml                              # Ant Build Script
└── .gitignore                             # Git Ignore Rules

```

---

## Package Structure Detail

### 1. Main Package (`com.oceanview.resort.main`)
**Purpose:** Application entry points

| File | Purpose |
|------|---------|
| `ReservationSystemMain.java` | Main application class - starts console application with authentication |
| `WebServiceMain.java` | Separate entry point to run web service independently |

---

### 2. Model Package (`com.oceanview.resort.model`)
**Purpose:** Domain models representing business entities

| File | Purpose | Key Components |
|------|---------|----------------|
| `User.java` | System user representation | username, password, role, fullName |
| `Guest.java` | Hotel guest information | name, address, contact, email, NIC |
| `Room.java` | Hotel room details | roomNumber, roomType (enum), rate, availability |
| `Reservation.java` | Booking information | reservationNumber, guest, room, dates, amount |

**RoomType Enum:**
- SINGLE (Rs. 5,000/night)
- DOUBLE (Rs. 8,000/night)
- DELUXE (Rs. 12,000/night)
- SUITE (Rs. 15,000/night)

---

### 3. DAO Package (`com.oceanview.resort.dao`)
**Purpose:** Data Access Object pattern - abstracts data persistence

| File | Purpose | Key Methods |
|------|---------|-------------|
| `UserDAO.java` | User data operations | save(), findByUsername(), findAll() |
| `RoomDAO.java` | Room data operations | save(), findByRoomNumber(), findAvailableRooms() |
| `ReservationDAO.java` | Reservation data operations | save(), findByReservationNumber(), search() |

**Data Files:**
- `data/users.dat` - User accounts
- `data/rooms.dat` - Room inventory
- `data/reservations.dat` - Booking records

---

### 4. Service Package (`com.oceanview.resort.service`)
**Purpose:** Business logic layer

| File | Purpose | Pattern |
|------|---------|---------|
| `PricingStrategy.java` | Interface for pricing algorithms | Strategy Pattern (Interface) |
| `StandardPricingStrategy.java` | Standard rate calculation | Strategy Pattern (Implementation) |
| `DiscountPricingStrategy.java` | Discount calculation (10% for 7+ nights) | Strategy Pattern (Implementation) |
| `BillingService.java` | Bill calculation and formatting | Uses Strategy Pattern |
| `ReservationService.java` | Core reservation business logic | Orchestrates DAOs and Services |

---

### 5. Factory Package (`com.oceanview.resort.factory`)
**Purpose:** Factory pattern for object creation

| File | Purpose | Methods |
|------|---------|---------|
| `ReservationFactory.java` | Creates reservations with unique numbers | createReservation() |
| `RoomFactory.java` | Creates different room types | createSingleRoom(), createDoubleRoom(), etc. |

---

### 6. UI Package (`com.oceanview.resort.ui`)
**Purpose:** User interface layer

| File | Purpose | Features |
|------|---------|----------|
| `MenuSystem.java` | Menu-driven console interface | 8 menu options, input handling, formatted output |

**Menu Options:**
1. Add New Reservation
2. Display Reservation Details
3. Calculate Bill
4. View Available Rooms
5. View All Reservations
6. Search Reservation by Guest Name
7. Help
8. Exit System

---

### 7. WebService Package (`com.oceanview.resort.webservice`)
**Purpose:** Distributed architecture - RESTful web services

| File | Purpose | Endpoints |
|------|---------|-----------|
| `ReservationWebService.java` | HTTP server with REST API | GET /reservations, POST /reservations, GET /rooms, GET /health |

**Web Service Endpoints:**
- `GET /health` - Health check
- `GET /reservations` - Get all reservations (JSON)
- `GET /reservations/{id}` - Get specific reservation (JSON)
- `POST /reservations` - Create new reservation (JSON)
- `GET /rooms` - Get available rooms (JSON)

---

### 8. Util Package (`com.oceanview.resort.util`)
**Purpose:** Utility classes

| File | Purpose | Pattern |
|------|---------|---------|
| `FileManager.java` | File I/O operations | Singleton Pattern |
| `ValidationUtil.java` | Input validation utilities | Static utility methods |

**FileManager Methods:**
- writeObjectToFile(), readObjectFromFile()
- writeListToFile(), readListFromFile()
- appendToFile(), readLinesFromFile()

**ValidationUtil Methods:**
- isValidEmail(), isValidPhone(), isValidDate()
- isDateInFuture(), isValidDateRange(), isValidName()

---

### 9. Security Package (`com.oceanview.resort.security`)
**Purpose:** Authentication and authorization

| File | Purpose | Methods |
|------|---------|---------|
| `AuthenticationService.java` | User authentication | login(), logout(), isAuthenticated() |

---

### 10. Test Package (`test/com.oceanview.resort`)
**Purpose:** Test classes

| File | Purpose | Test Coverage |
|------|---------|---------------|
| `service/ReservationServiceTest.java` | Service layer tests | Reservation creation, bill calculation, validation |
| `util/ValidationUtilTest.java` | Utility tests | Email, phone, date validation |

---

## File Count Summary

### Source Files
- **Main Classes:** 2
- **Model Classes:** 4
- **DAO Classes:** 3
- **Service Classes:** 5
- **Factory Classes:** 2
- **UI Classes:** 1
- **WebService Classes:** 1
- **Util Classes:** 2
- **Security Classes:** 1
- **Total Java Source Files:** 21

### Test Files
- **Test Classes:** 2
- **Total Test Files:** 2

### Documentation Files
- **Markdown Files:** 8+
- **Configuration Files:** 3
- **Total Documentation:** 11+

### Data Files (Runtime)
- **Data Files:** 3 (.dat files)

---

## File Size Estimates

```
Source Code:        ~2,500 lines
Documentation:      ~4,000 words
Test Code:          ~500 lines
Configuration:      ~200 lines
Total:              ~7,200 lines
```

---

## Key Directories Explained

### `/src`
Contains all Java source code organized in packages. This is the main development directory.

### `/test`
Contains test classes that verify the functionality of the application.

### `/data`
Created at runtime to store serialized data files. Contains:
- User accounts
- Room inventory
- Reservation records

### `/docs`
Documentation files including:
- Design documents
- Test plans
- UML descriptions
- Implementation guides

### `/build`
Compiled Java class files (.class). Created when you compile the project.

### `/dist`
Distribution directory containing JAR files. Created when you build the project.

### `/.github/workflows`
GitHub Actions CI/CD pipeline configuration for automated building and testing.

### `/nbproject`
NetBeans IDE project configuration files. Required for NetBeans to recognize and manage the project.

---

## Package Dependencies

```
main (entry point)
  ├── ui (uses)
  │     └── service (uses)
  │           ├── dao (uses)
  │           │     └── util (FileManager)
  │           ├── factory
  │           └── model
  ├── security (uses)
  │     └── dao
  └── webservice (uses)
        └── service

All packages → model (domain entities)
```

---

## Data Flow

```
User Input (Console)
    ↓
MenuSystem (UI Layer)
    ↓
ReservationService (Business Logic)
    ↓
ReservationDAO / RoomDAO (Data Access)
    ↓
FileManager (Singleton - File I/O)
    ↓
data/*.dat files (Persistent Storage)
```

---

## Design Patterns Location

| Pattern | Location | Files |
|---------|----------|-------|
| **Singleton** | util package | FileManager.java |
| **Factory** | factory package | ReservationFactory.java, RoomFactory.java |
| **DAO** | dao package | UserDAO.java, RoomDAO.java, ReservationDAO.java |
| **Strategy** | service package | PricingStrategy.java, StandardPricingStrategy.java, DiscountPricingStrategy.java |

---

## Build Output Structure

After compiling (`Clean and Build` in NetBeans):

```
build/
└── classes/
    └── com/
        └── oceanview/
            └── resort/
                ├── main/
                │   ├── ReservationSystemMain.class
                │   └── WebServiceMain.class
                ├── model/
                │   ├── User.class
                │   ├── Guest.class
                │   ├── Room.class
                │   └── Reservation.class
                ├── dao/
                ├── service/
                ├── factory/
                ├── ui/
                ├── webservice/
                ├── util/
                └── security/

dist/
└── Ocean View Resort - Room Reservation System.jar
```

---

## Import Dependencies

All classes use only **Java Standard Library** - no external libraries:

- `java.io.*` - File I/O
- `java.time.*` - Date handling
- `java.util.*` - Collections, Lists
- `java.net.*` - Network (web services)
- `java.util.regex.*` - Regular expressions (validation)

**No Maven, Gradle, or external JAR files required!**

---

## NetBeans Project View

In NetBeans IDE, the project structure appears as:

```
OceanViewResort
├── Source Packages
│   └── com.oceanview.resort
│       ├── main (2 files)
│       ├── model (4 files)
│       ├── dao (3 files)
│       ├── service (5 files)
│       ├── factory (2 files)
│       ├── ui (1 file)
│       ├── webservice (1 file)
│       ├── util (2 files)
│       └── security (1 file)
├── Test Packages
│   └── com.oceanview.resort
│       ├── service (1 file)
│       └── util (1 file)
├── Important Files
│   ├── README.md
│   ├── build.xml
│   └── nbproject/
└── Data Files (runtime)
    └── data/
```

---

## Quick Reference: File Locations

### Need to modify...?

**Login credentials:**
→ `dao/UserDAO.java` - `initializeDefaultUsers()`

**Room types and rates:**
→ `model/Room.java` - `RoomType` enum

**Discount policy:**
→ `service/DiscountPricingStrategy.java`

**Menu options:**
→ `ui/MenuSystem.java` - `displayMainMenu()`

**Web service port:**
→ `webservice/ReservationWebService.java` - Constructor

**Default rooms:**
→ `dao/RoomDAO.java` - `initializeDefaultRooms()`

**Data storage location:**
→ `util/FileManager.java` - `DATA_DIR` constant

**Main class:**
→ `main/ReservationSystemMain.java`

---

This structure follows **clean architecture principles** with clear separation of concerns:
- **Presentation** (UI, WebService)
- **Business Logic** (Service)
- **Data Access** (DAO)
- **Utilities** (Util, Factory)

---

**Last Updated:** January 2026

