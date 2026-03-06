# Ocean View Resort - Room Reservation System

**Module:** CIS6003 Advanced Programming  
**Assessment:** WRIT1 - Online Room Reservation System  
**Weighting:** 100%  
**Technology:** Pure Java (Java SE 17) - No External Libraries

## Overview

A comprehensive web-based room reservation system for Ocean View Resort in Galle, Sri Lanka. Built entirely with pure Java using a built-in HTTP server (no external libraries required).

## Features

- ✅ User Authentication (Login/Logout)
- ✅ Add New Reservation
- ✅ View Reservation Details
- ✅ Calculate Bill
- ✅ View Available Rooms
- ✅ Search Reservations
- ✅ Help Section
- ✅ Web-based Interface
- ✅ File-based Data Storage (No database setup required)

## Quick Start
### Prerequisites
- **Java JDK 17 or higher**
- A command line terminal

### Running the Application

1.  **Open Terminal** in the project folder.
2.  **Compile the code**:
    ```bash
    # Windows
    if (-not (Test-Path "build/classes")) { New-Item -ItemType Directory -Force -Path "build/classes" }
    javac -d build/classes (Get-ChildItem -Recurse -Filter *.java src).FullName
    
    # Linux/Mac
    mkdir -p build/classes
    find src -name "*.java" > sources.txt
    javac -d build/classes @sources.txt
    ```
3.  **Run the Server**:
    ```bash
    java -cp build/classes com.oceanview.resort.web.WebServer
    ```
4.  **Access in Browser**:
    -   Open: http://localhost:8080
    -   Login: `admin` / `admin123`

## Project Structure

```
ocean-view-resort/
├── src/                    # Source code (Java)
├── web/                    # HTML/CSS/JS files
├── data/                   # Data storage (created at runtime)
├── test/                   # Test classes
├── build/                  # Compiled classes
└── docs/                   # Documentation
```

## Design Patterns

- **Singleton** (FileManager)
- **Factory** (ReservationFactory)
- **DAO** (Data Access Objects)
- **Strategy** (Pricing Strategy)

## Documentation

See `docs/` folder for:
- Design Document
- Test Plan
- UML Diagrams
- Implementation Guide

## Author

CIS6003 Advanced Programming Assessment
