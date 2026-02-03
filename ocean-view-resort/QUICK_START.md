# Quick Start Guide
## Ocean View Resort - Room Reservation System

## Prerequisites

- Java JDK 17 or higher
- NetBeans IDE 12.0 or higher (recommended)
- Git (for version control)

## Setting Up the Project in NetBeans

### Step 1: Open Project in NetBeans

1. Launch NetBeans IDE
2. Go to **File → Open Project**
3. Navigate to the `ocean-view-resort` folder
4. Select the project and click **Open**

### Step 2: Set Main Class

1. Right-click on the project in Projects window
2. Select **Properties**
3. Go to **Run** category
4. Set **Main Class** to: `com.oceanview.resort.main.ReservationSystemMain`
5. Click **OK**

### Step 3: Build the Project

1. Right-click on project
2. Select **Clean and Build**
3. Wait for build to complete (should show "BUILD SUCCESSFUL")

### Step 4: Run the Application

1. Right-click on project
2. Select **Run**
3. The application will start in the console

## Running from Command Line

### Compile the Project

```bash
cd ocean-view-resort
mkdir -p build/classes
javac -d build/classes src/com/oceanview/resort/**/*.java
```

**Note:** On Windows, use:
```bash
javac -d build/classes src/com/oceanview/resort/main/*.java src/com/oceanview/resort/model/*.java src/com/oceanview/resort/dao/*.java src/com/oceanview/resort/service/*.java src/com/oceanview/resort/ui/*.java src/com/oceanview/resort/util/*.java src/com/oceanview/resort/factory/*.java src/com/oceanview/resort/security/*.java src/com/oceanview/resort/webservice/*.java
```

### Run the Application

```bash
java -cp build/classes com.oceanview.resort.main.ReservationSystemMain
```

## Default Login Credentials

**Administrator:**
- Username: `admin`
- Password: `admin123`

**Staff:**
- Username: `staff`
- Password: `staff123`

## Running the Web Service (Optional)

The web service runs on a separate thread and can be started along with the main application or independently.

### Start Web Service in Main Application

The web service will start automatically when you run the main application. It runs on port 8080 by default.

### Test Web Service Endpoints

Once running, you can test the endpoints using curl or a web browser:

```bash
# Health check
curl http://localhost:8080/health

# Get all reservations
curl http://localhost:8080/reservations

# Get available rooms
curl http://localhost:8080/rooms

# Get specific reservation
curl http://localhost:8080/reservations/RES-1234567-1000
```

## Running Tests

### Run Tests from Command Line

```bash
# Compile test classes
javac -d build/classes -cp build/classes test/com/oceanview/resort/**/*.java

# Run ReservationService tests
java -cp build/classes com.oceanview.resort.service.ReservationServiceTest

# Run ValidationUtil tests
java -cp build/classes com.oceanview.resort.util.ValidationUtilTest
```

## Project Structure

```
ocean-view-resort/
├── src/
│   └── com/oceanview/resort/
│       ├── main/           # Main application
│       ├── model/          # Domain models
│       ├── dao/            # Data access objects
│       ├── factory/        # Factory patterns
│       ├── service/        # Business logic
│       ├── ui/             # User interface
│       ├── webservice/     # Web services
│       ├── util/           # Utilities
│       └── security/       # Authentication
├── test/                   # Test classes
├── data/                   # Data files (created at runtime)
├── docs/                   # Documentation
└── build/                  # Compiled classes (created on build)
```

## Data Storage

Data files are stored in the `data/` directory:
- `users.dat` - User accounts
- `reservations.dat` - Reservation records
- `rooms.dat` - Room information

**Note:** The `data/` directory is created automatically when the application first runs.

## Troubleshooting

### Issue: "ClassNotFoundException"

**Solution:** Make sure all Java files are compiled. Clean and rebuild the project.

### Issue: "File not found" errors

**Solution:** The `data/` directory should be created automatically. If not, create it manually:
```bash
mkdir data
```

### Issue: "Port already in use" (for web service)

**Solution:** Another application is using port 8080. Either:
1. Stop the other application
2. Modify the port in `ReservationWebService.java`

### Issue: Compilation errors on Windows

**Solution:** Windows doesn't support wildcards in javac. Use the explicit command shown above or use NetBeans IDE.

## Features Overview

1. **User Authentication** - Login with username/password
2. **Add Reservation** - Create new room bookings
3. **View Reservations** - Display booking details
4. **Calculate Bill** - Generate detailed bills
5. **View Rooms** - See available rooms
6. **Search** - Find reservations by guest name
7. **Help** - System usage guide
8. **Web Service** - REST API for distributed access

## Getting Help

- Check the Help section in the application (Option 7)
- Review documentation in the `docs/` folder
- See `TEST_PLAN.md` for validation rules
- Check `DESIGN_DOCUMENT.md` for system architecture

## Next Steps

1. Log in to the system
2. Explore the menu options
3. Create a test reservation
4. View the generated bill
5. Test the web service endpoints

---

**For detailed documentation, see:**
- `README.md` - Project overview
- `docs/DESIGN_DOCUMENT.md` - System design
- `docs/TEST_PLAN.md` - Testing documentation
- `docs/UML_DIAGRAMS.md` - UML diagram descriptions
