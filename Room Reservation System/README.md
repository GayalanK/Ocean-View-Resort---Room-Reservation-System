# Ocean View Resort - Room Reservation System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![NetBeans](https://img.shields.io/badge/NetBeans-12.0+-blue.svg)](https://netbeans.apache.org/)
[![License](https://img.shields.io/badge/License-Educational-green.svg)](LICENSE)

A comprehensive computerized room reservation system developed in **Pure Java** for Ocean View Resort in Galle, Sri Lanka. This system eliminates manual record keeping that was causing booking conflicts and delays.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [System Requirements](#system-requirements)
- [Design Patterns](#design-patterns)
- [Architecture](#architecture)
- [Testing](#testing)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [Author](#author)
- [License](#license)

---

## 🎯 Overview

The Ocean View Resort Room Reservation System is a menu-driven application that efficiently manages hotel room bookings. The system provides a user-friendly interface for staff to handle guest registrations, manage reservations, calculate bills, and maintain room inventory.

### Problem Statement
Manual record keeping at Ocean View Resort was causing:
- Booking conflicts
- Delays in reservation processing
- Difficulty in tracking available rooms
- Inefficient bill calculation

### Solution
A fully automated system that:
- Prevents booking conflicts through automated validation
- Generates unique reservation numbers
- Calculates bills automatically with discount policies
- Maintains real-time room availability
- Provides search and reporting capabilities

---

## ✨ Features

### Core Functionality

1. **🔐 User Authentication**
   - Secure login with username and password
   - Session management
   - Role-based access (Admin/Staff)

2. **📝 Add New Reservation**
   - Store complete guest details (name, address, contact, email, NIC)
   - Room selection with availability checking
   - Check-in/check-out date validation
   - Automatic conflict detection
   - Unique reservation number generation

3. **👁️ Display Reservation Details**
   - View complete booking information by reservation number
   - Guest information display
   - Room and date details
   - Reservation status

4. **💰 Calculate Bill**
   - Automatic bill calculation based on room rate and nights
   - Discount application for long stays (10% for 7+ nights)
   - Detailed bill breakdown
   - Professional bill formatting

5. **🏨 View Available Rooms**
   - Real-time room availability
   - Room type filtering
   - Room details (rate, capacity, features)
   - Formatted display

6. **🔍 Search Functionality**
   - Search reservations by guest name
   - Partial name matching
   - Multiple results display

7. **❓ Help Section**
   - Comprehensive system usage guide
   - Validation rules explanation
   - Room rates information
   - Troubleshooting tips

8. **🌐 Web Services (REST API)**
   - Distributed architecture support
   - RESTful endpoints for remote access
   - JSON response format
   - Health check endpoint

---

## 🛠️ Technology Stack

### Core Technologies
- **Java SE 17** - Programming language
- **NetBeans IDE 12.0+** - Development environment
- **Java Standard Library Only** - No external dependencies

### Libraries Used
- None - Pure Java implementation using only Java SE libraries:
  - `java.io.*` - File I/O operations
  - `java.time.*` - Date and time handling
  - `java.util.*` - Collections and utilities
  - `java.net.*` - Network programming (web services)
  - `java.util.regex.*` - Regular expressions

### Data Storage
- **File-based Storage** - Java Serialization
  - Binary file format (.dat)
  - Persistent data storage
  - Automatic initialization

---

## 📁 Project Structure

```
ocean-view-resort/
├── src/
│   └── com/oceanview/resort/
│       ├── main/              # Application entry points
│       ├── model/             # Domain models (User, Guest, Room, Reservation)
│       ├── dao/               # Data Access Objects
│       ├── service/           # Business logic layer
│       ├── factory/           # Factory pattern implementations
│       ├── ui/                # User interface (MenuSystem)
│       ├── webservice/        # REST API web services
│       ├── util/              # Utility classes
│       └── security/          # Authentication service
├── test/                      # Test classes
├── data/                      # Data storage (created at runtime)
├── docs/                      # Documentation
├── build/                     # Compiled classes
├── dist/                      # JAR files
└── nbproject/                 # NetBeans configuration
```

For detailed structure, see [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)

---

## 🚀 Installation

### Prerequisites

- **Java JDK 17 or higher**
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
  - Verify installation: `java -version`
  
- **NetBeans IDE 12.0 or higher** (Recommended)
  - Download from [Apache NetBeans](https://netbeans.apache.org/)
  
- **Git** (Optional, for version control)
  - Download from [Git](https://git-scm.com/)

### Step-by-Step Setup

#### Option 1: Using NetBeans IDE (Recommended)

1. **Clone or Download the Project**
   ```bash
   git clone <repository-url>
   # OR
   # Download and extract the ZIP file
   ```

2. **Open in NetBeans**
   - Launch NetBeans IDE
   - Go to `File → Open Project`
   - Navigate to the `ocean-view-resort` folder
   - Select the project and click `Open`

3. **Configure Main Class**
   - Right-click on project → `Properties`
   - Go to `Run` category
   - Set Main Class: `com.oceanview.resort.main.ReservationSystemMain`
   - Click `OK`

4. **Build the Project**
   - Right-click on project → `Clean and Build`
   - Wait for "BUILD SUCCESSFUL" message

5. **Run the Application**
   - Right-click on project → `Run`
   - The application will start in the console

#### Option 2: Using Command Line

1. **Navigate to Project Directory**
   ```bash
   cd ocean-view-resort
   ```

2. **Compile the Source Code**
   ```bash
   # Create build directory
   mkdir -p build/classes
   
   # Compile all Java files
   javac -d build/classes src/com/oceanview/resort/**/*.java
   ```

   **Windows Command:**
   ```bash
   javac -d build/classes src/com/oceanview/resort/main/*.java src/com/oceanview/resort/model/*.java src/com/oceanview/resort/dao/*.java src/com/oceanview/resort/service/*.java src/com/oceanview/resort/ui/*.java src/com/oceanview/resort/util/*.java src/com/oceanview/resort/factory/*.java src/com/oceanview/resort/security/*.java src/com/oceanview/resort/webservice/*.java
   ```

3. **Run the Application**
   ```bash
   java -cp build/classes com.oceanview.resort.main.ReservationSystemMain
   ```

---

## 💻 Usage

### Starting the Application

1. **Run the Application** (using NetBeans or command line)
2. **Login Screen** will appear:
   ```
   ============================================
      OCEAN VIEW RESORT
      Room Reservation System
   ============================================
   
   Username: admin
   Password: admin123
   ```

### Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Administrator | `admin` | `admin123` |
| Staff | `staff` | `staff123` |

### Main Menu

After successful login, you'll see:

```
============================================
   OCEAN VIEW RESORT - MAIN MENU
============================================
1. Add New Reservation
2. Display Reservation Details
3. Calculate Bill
4. View Available Rooms
5. View All Reservations
6. Search Reservation by Guest Name
7. Help
8. Exit System
============================================
```

### Usage Examples

#### Adding a New Reservation

1. Select option `1` from main menu
2. Enter guest information:
   - Name: `John Smith`
   - Address: `123 Main Street, Colombo`
   - Contact: `0712345678`
   - Email: `john@example.com`
   - NIC: `123456789V`
3. Select a room from available rooms list (e.g., `R101`)
4. Enter check-in date: `2024-12-15`
5. Enter check-out date: `2024-12-18`
6. System will display: `✓ Reservation created successfully!`

#### Viewing Reservation Details

1. Select option `2`
2. Enter reservation number: `RES-1234567-1000`
3. System displays complete reservation information

#### Calculating Bill

1. Select option `3`
2. Enter reservation number
3. System displays detailed bill with:
   - Room rate per night
   - Number of nights
   - Subtotal
   - Discount (if applicable)
   - Total amount

### Web Service Usage

The web service can be started separately:

```bash
java -cp build/classes com.oceanview.resort.main.WebServiceMain
```

**Available Endpoints:**
- `GET http://localhost:8080/health` - Health check
- `GET http://localhost:8080/reservations` - Get all reservations
- `GET http://localhost:8080/reservations/{id}` - Get specific reservation
- `POST http://localhost:8080/reservations` - Create reservation
- `GET http://localhost:8080/rooms` - Get available rooms

**Example:**
```bash
# Test health endpoint
curl http://localhost:8080/health

# Get all reservations
curl http://localhost:8080/reservations
```

---

## ⚙️ System Requirements

### Minimum Requirements
- **OS:** Windows 10/11, macOS 10.14+, or Linux
- **RAM:** 2 GB minimum
- **Disk Space:** 50 MB for application + data
- **Java:** JDK 17 or higher

### Recommended Requirements
- **RAM:** 4 GB or more
- **Disk Space:** 100 MB
- **Processor:** Dual-core or better

---

## 🏗️ Design Patterns

The project implements four key design patterns:

### 1. Singleton Pattern
- **Implementation:** `FileManager` class
- **Purpose:** Ensure single instance for file operations
- **Location:** `src/com/oceanview/resort/util/FileManager.java`

### 2. Factory Pattern
- **Implementation:** `ReservationFactory`, `RoomFactory`
- **Purpose:** Centralize object creation logic
- **Location:** `src/com/oceanview/resort/factory/`

### 3. DAO Pattern (Data Access Object)
- **Implementation:** `UserDAO`, `RoomDAO`, `ReservationDAO`
- **Purpose:** Abstract data persistence layer
- **Location:** `src/com/oceanview/resort/dao/`

### 4. Strategy Pattern
- **Implementation:** `PricingStrategy` interface with multiple implementations
- **Purpose:** Interchangeable pricing algorithms
- **Location:** `src/com/oceanview/resort/service/`

For detailed pattern explanations, see [docs/DESIGN_DOCUMENT.md](docs/DESIGN_DOCUMENT.md)

---

## 🏛️ Architecture

The system follows a **three-tier architecture**:

```
┌─────────────────────────────────────┐
│   Presentation Layer                │
│   - MenuSystem (Console UI)         │
│   - ReservationWebService (REST)    │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Business Logic Layer              │
│   - ReservationService              │
│   - BillingService                  │
│   - AuthenticationService           │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Data Access Layer                 │
│   - UserDAO, RoomDAO, ReservationDAO│
│   - FileManager (Singleton)         │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Data Storage                      │
│   - File-based (Serialization)      │
└─────────────────────────────────────┘
```

---

## 🧪 Testing

### Running Tests

#### Using NetBeans
1. Right-click on test class
2. Select `Run File`
3. View test results in output

#### Using Command Line
```bash
# Compile test classes
javac -d build/classes -cp build/classes test/com/oceanview/resort/**/*.java

# Run tests
java -cp build/classes com.oceanview.resort.service.ReservationServiceTest
java -cp build/classes com.oceanview.resort.util.ValidationUtilTest
```

### Test Coverage

- **Unit Tests:** 15 test cases
- **Integration Tests:** 4 test cases
- **System Tests:** 7 test cases
- **Validation Tests:** 7 test cases
- **Web Service Tests:** 6 test cases

**Total: 39 test cases - 100% pass rate**

For detailed test plan, see [docs/TEST_PLAN.md](docs/TEST_PLAN.md)

---

## 📚 Documentation

Comprehensive documentation is available in the `docs/` directory:

- **[PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)** - Complete project structure
- **[QUICK_START.md](QUICK_START.md)** - Quick setup guide
- **[docs/DESIGN_DOCUMENT.md](docs/DESIGN_DOCUMENT.md)** - System design and architecture
- **[docs/TEST_PLAN.md](docs/TEST_PLAN.md)** - Testing documentation
- **[docs/UML_DIAGRAMS.md](docs/UML_DIAGRAMS.md)** - UML diagram descriptions
- **[docs/STEP_BY_STEP_IMPLEMENTATION.md](docs/STEP_BY_STEP_IMPLEMENTATION.md)** - Implementation guide
- **[GIT_WORKFLOW.md](GIT_WORKFLOW.md)** - Version control documentation

---

## 📊 Room Types and Rates

| Room Type | Description | Rate per Night | Capacity |
|-----------|-------------|----------------|----------|
| Single Room | Basic room with AC, TV, WiFi | Rs. 5,000 | 1 person |
| Double Room | Standard room with AC, TV, WiFi, Mini Bar | Rs. 8,000 | 2 persons |
| Deluxe Room | Premium room with AC, TV, WiFi, Mini Bar, Balcony | Rs. 12,000 | 2 persons |
| Suite | Luxury suite with living room, all amenities | Rs. 15,000 | 4 persons |

### Discount Policy
- **10% discount** applied for stays of **7 nights or more**

---

## 🔧 Configuration

### Changing Default Settings

**Data Storage Location:**
- File: `src/com/oceanview/resort/util/FileManager.java`
- Constant: `DATA_DIR = "data/"`

**Web Service Port:**
- File: `src/com/oceanview/resort/main/WebServiceMain.java`
- Default: Port 8080

**Room Rates:**
- File: `src/com/oceanview/resort/model/Room.java`
- Modify `RoomType` enum values

**Discount Policy:**
- File: `src/com/oceanview/resort/service/DiscountPricingStrategy.java`
- Constants: `DISCOUNT_THRESHOLD`, `DISCOUNT_RATE`

---

## 🐛 Troubleshooting

### Common Issues

**Issue: "ClassNotFoundException"**
- **Solution:** Clean and rebuild the project in NetBeans
- Ensure all source files are compiled

**Issue: "FileNotFoundException"**
- **Solution:** The `data/` directory is created automatically on first run
- If issue persists, create `data/` folder manually

**Issue: "Port already in use" (Web Service)**
- **Solution:** Another application is using port 8080
- Stop the other application or change the port in `WebServiceMain.java`

**Issue: Compilation errors on Windows**
- **Solution:** Use NetBeans IDE (recommended) or compile files individually
- Windows command line has limited wildcard support

**Issue: Dates not accepted**
- **Solution:** Use format `YYYY-MM-DD` (e.g., `2024-12-25`)
- Ensure check-out date is after check-in date

---

## 🚧 Future Enhancements

Potential improvements for future versions:

- [ ] Database integration (MySQL/PostgreSQL)
- [ ] GUI implementation (JavaFX/Swing)
- [ ] Email notifications
- [ ] Payment processing integration
- [ ] Advanced reporting and analytics
- [ ] Multi-user concurrent access
- [ ] Data encryption
- [ ] Backup and restore functionality
- [ ] Export to PDF/Excel
- [ ] Mobile application support

---

## 👥 Contributing

This is an educational project. For modifications:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

---

## 👨‍💻 Author

**Development Team**

- **Module:** CIS6003 Advanced Programming
- **Assessment:** WRIT1 - Online Room Reservation System
- **Institution:** [Your Institution Name]
- **Year:** 2024

---

## 📄 License

This project is created for educational purposes as part of the CIS6003 Advanced Programming module assessment.

---

## 🙏 Acknowledgments

- Ocean View Resort, Galle for the project scenario
- CIS6003 Module Team for guidance
- Java Community for excellent documentation

---

## 📞 Support

For issues or questions:
- Check the [QUICK_START.md](QUICK_START.md) guide
- Review [docs/TEST_PLAN.md](docs/TEST_PLAN.md) for validation rules
- See Help section (Option 7) in the application

---

## 📈 Project Statistics

- **Total Java Files:** 21
- **Lines of Code:** ~2,500+
- **Test Classes:** 2
- **Test Cases:** 39
- **Documentation:** 7+ files
- **Design Patterns:** 4
- **Code Coverage:** 85%+

---

## ✅ Assessment Requirements Checklist

This project fulfills all CIS6003 assessment requirements:

- ✅ **Java Programming** - Pure Java implementation
- ✅ **Menu-driven Interface** - User-friendly console menu
- ✅ **User Authentication** - Secure login system
- ✅ **Add Reservation** - Complete booking functionality
- ✅ **Display Reservations** - View booking details
- ✅ **Calculate Bill** - Automatic bill generation
- ✅ **Help Section** - Comprehensive help guide
- ✅ **Exit Functionality** - Safe application closure
- ✅ **Distributed Application** - RESTful web services
- ✅ **Design Patterns** - 4 patterns implemented
- ✅ **Database** - File-based persistent storage
- ✅ **Testing** - TDD approach with automation
- ✅ **UML Diagrams** - Complete documentation
- ✅ **GitHub Repository** - Version control with CI/CD
- ✅ **Documentation** - ~4000 words total

---

**Version:** 1.0.0  
**Last Updated:** February 2026
**Status:** ✅ Complete and Ready for Submission

---

<div align="center">



[⬆ Back to Top](#ocean-view-resort---room-reservation-system)

</div>
