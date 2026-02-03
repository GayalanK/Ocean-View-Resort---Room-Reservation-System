# Ocean View Resort - Room Reservation System

**Module:** CIS6003 Advanced Programming  
**Assessment:** WRIT1 - Online Room Reservation System  
**Weighting:** 100%  
**Technology:** Pure Java (Java SE 17) - No External Libraries

## Overview

A comprehensive web-based room reservation system for Ocean View Resort in Galle, Sri Lanka. Built entirely with pure Java using built-in HTTP server (no external libraries).

## Features

- ✅ User Authentication (Login/Logout)
- ✅ Add New Reservation
- ✅ View Reservation Details
- ✅ Calculate Bill
- ✅ View Available Rooms
- ✅ Search Reservations
- ✅ Help Section
- ✅ Web-based Interface
- ✅ File-based Data Storage

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

## Project Structure

```
ocean-view-resort/
├── src/                    # Source code
├── web/                    # HTML/CSS/JS files
├── data/                   # Data storage (created at runtime)
├── test/                   # Test classes
└── docs/                   # Documentation
```

## Design Patterns

- Singleton (FileManager)
- Factory (ReservationFactory)
- DAO (Data Access Objects)
- Strategy (Pricing Strategy)

## Documentation

See `docs/` folder for:
- Design Document
- Test Plan
- UML Diagrams
- Implementation Guide

## Author

CIS6003 Advanced Programming Assessment
